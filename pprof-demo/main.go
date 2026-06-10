package main

import (
	"fmt"
	"log/slog"
	"net/http"
	_ "net/http/pprof"
	"os"
	"runtime"
	"strconv"
	"sync"
	"sync/atomic"
	"time"
)

var (
	retainedMu sync.Mutex
	retained   [][]byte
	cpuSink    atomic.Uint64
)

func main() {
	runtime.SetBlockProfileRate(1)
	runtime.SetMutexProfileFraction(1)

	mux := http.NewServeMux()
	mux.HandleFunc("/", index)
	mux.HandleFunc("/work/cpu", cpuWork)
	mux.HandleFunc("/work/heap", heapWork)
	mux.HandleFunc("/work/heap/clear", clearHeap)
	mux.HandleFunc("/work/mutex", mutexWork)
	mux.HandleFunc("/work/block", blockWork)
	mux.Handle("/debug/pprof/", http.DefaultServeMux)

	addr := ":6060"
	slog.Info("pprof demo is listening", "addr", addr)
	if err := http.ListenAndServe(addr, mux); err != nil {
		slog.Error("server stopped", "error", err)
		os.Exit(1)
	}
}

func index(w http.ResponseWriter, _ *http.Request) {
	_, _ = fmt.Fprint(w, `pprof demo

Try:
  curl "http://localhost:6060/work/cpu?seconds=10"
  curl "http://localhost:6060/work/heap?mb=128"
  curl "http://localhost:6060/work/mutex?seconds=10&workers=32"
  curl "http://localhost:6060/work/block?seconds=10&workers=32"

Profiles:
  http://localhost:6060/debug/pprof/
`)
}

func cpuWork(w http.ResponseWriter, r *http.Request) {
	seconds := intParam(r, "seconds", 10, 1, 60)
	workers := intParam(r, "workers", runtime.NumCPU(), 1, 256)
	deadline := time.Now().Add(time.Duration(seconds) * time.Second)

	for range workers {
		go burnCPU(deadline)
	}

	_, _ = fmt.Fprintf(w, "cpu work started, seconds=%d, workers=%d\n", seconds, workers)
}

func burnCPU(deadline time.Time) {
	var n uint64 = 1
	for time.Now().Before(deadline) {
		n = nextPrime(n + 1)
	}
	cpuSink.Store(n)
}

func heapWork(w http.ResponseWriter, r *http.Request) {
	mb := intParam(r, "mb", 128, 1, 1024)
	chunk := make([]byte, mb*1024*1024)
	for i := range chunk {
		chunk[i] = byte(i)
	}

	retainedMu.Lock()
	retained = append(retained, chunk)
	total := len(retained)
	retainedMu.Unlock()

	_, _ = fmt.Fprintf(w, "retained %d MiB, retained chunks=%d\n", mb, total)
}

func clearHeap(w http.ResponseWriter, _ *http.Request) {
	retainedMu.Lock()
	retained = nil
	retainedMu.Unlock()
	runtime.GC()

	_, _ = fmt.Fprintln(w, "retained heap cleared")
}

func mutexWork(w http.ResponseWriter, r *http.Request) {
	seconds := intParam(r, "seconds", 10, 1, 60)
	workers := intParam(r, "workers", 32, 1, 256)
	deadline := time.Now().Add(time.Duration(seconds) * time.Second)

	var mu sync.Mutex
	var count uint64
	var wg sync.WaitGroup
	wg.Add(workers)

	for range workers {
		go func() {
			defer wg.Done()
			for time.Now().Before(deadline) {
				mu.Lock()
				count++
				time.Sleep(200 * time.Microsecond)
				mu.Unlock()
			}
		}()
	}

	wg.Wait()
	_, _ = fmt.Fprintf(w, "mutex work finished, workers=%d, count=%d\n", workers, count)
}

func blockWork(w http.ResponseWriter, r *http.Request) {
	seconds := intParam(r, "seconds", 10, 1, 60)
	workers := intParam(r, "workers", 32, 1, 256)
	done := make(chan struct{})
	time.AfterFunc(time.Duration(seconds)*time.Second, func() {
		close(done)
	})
	ch := make(chan struct{})

	var wg sync.WaitGroup
	wg.Add(workers)
	for range workers {
		go func() {
			defer wg.Done()
			for {
				select {
				case <-done:
					return
				case <-ch:
				}
			}
		}()
	}

	wg.Wait()
	_, _ = fmt.Fprintf(w, "block work finished, workers=%d\n", workers)
}

func intParam(r *http.Request, name string, fallback, minValue, maxValue int) int {
	raw := r.URL.Query().Get(name)
	if raw == "" {
		return fallback
	}

	value, err := strconv.Atoi(raw)
	if err != nil || value < minValue || value > maxValue {
		return fallback
	}

	return value
}

func nextPrime(start uint64) uint64 {
	for n := start; ; n++ {
		if isPrime(n) {
			return n
		}
	}
}

func isPrime(n uint64) bool {
	if n < 2 {
		return false
	}
	for i := uint64(2); i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
