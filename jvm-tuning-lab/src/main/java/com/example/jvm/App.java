package com.example.jvm;

import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception {
        String mode = args.length == 0 ? "help" : args[0];
        printBanner(mode);

        switch (mode) {
            case "idle" -> idle();
            case "heap-leak" -> heapLeak(args);
            case "gc-churn" -> gcChurn(args);
            case "cpu-hot" -> cpuHot();
            case "deadlock" -> deadlock();
            case "thread-block" -> threadBlock(args);
            default -> printHelp();
        }
    }

    private static void printBanner(String mode) {
        String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
        String pid = runtimeName.substring(0, runtimeName.indexOf('@'));
        System.out.printf("mode=%s pid=%s time=%s%n", mode, pid, LocalTime.now());
    }

    private static void printHelp() {
        System.out.println("""
                Usage:
                  java -jar target/jvm-tuning-lab-1.0.0.jar idle
                  java -Xms64m -Xmx64m -jar target/jvm-tuning-lab-1.0.0.jar heap-leak [chunkMb] [sleepMs]
                  java -Xms128m -Xmx128m -jar target/jvm-tuning-lab-1.0.0.jar gc-churn [chunkKb]
                  java -jar target/jvm-tuning-lab-1.0.0.jar cpu-hot
                  java -jar target/jvm-tuning-lab-1.0.0.jar deadlock
                  java -jar target/jvm-tuning-lab-1.0.0.jar thread-block [threads]
                """);
    }

    private static void idle() throws InterruptedException {
        while (true) {
            logMemory();
            TimeUnit.SECONDS.sleep(5);
        }
    }

    private static void heapLeak(String[] args) throws InterruptedException {
        int chunkMb = intArg(args, 1, 2);
        int sleepMs = intArg(args, 2, 500);
        List<byte[]> retained = new ArrayList<>();

        while (true) {
            retained.add(new byte[chunkMb * 1024 * 1024]);
            System.out.printf("retainedChunks=%d retainedMb=%d%n", retained.size(), retained.size() * chunkMb);
            TimeUnit.MILLISECONDS.sleep(sleepMs);
        }
    }

    private static void gcChurn(String[] args) {
        int chunkKb = intArg(args, 1, 256);
        long batches = 0;

        while (true) {
            for (int i = 0; i < 10_000; i++) {
                byte[] garbage = new byte[chunkKb * 1024];
                garbage[0] = (byte) i;
            }
            batches++;
            if (batches % 20 == 0) {
                System.out.printf("garbageBatches=%d%n", batches);
            }
        }
    }

    private static void cpuHot() {
        int workers = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
        ExecutorService pool = Executors.newFixedThreadPool(workers);
        for (int i = 0; i < workers; i++) {
            pool.submit(App::burnCpu);
        }
    }

    private static void burnCpu() {
        long n = 10_000_000L;
        while (true) {
            if (isPrime(n)) {
                System.out.printf("%s prime=%d%n", Thread.currentThread().getName(), n);
            }
            n++;
        }
    }

    private static boolean isPrime(long n) {
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static void deadlock() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        CountDownLatch started = new CountDownLatch(2);

        Thread t1 = new Thread(() -> lockInOrder(lockA, lockB, started), "deadlock-a-then-b");
        Thread t2 = new Thread(() -> lockInOrder(lockB, lockA, started), "deadlock-b-then-a");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void lockInOrder(Object first, Object second, CountDownLatch started) {
        synchronized (first) {
            started.countDown();
            await(started);
            sleep(200);
            synchronized (second) {
                System.out.println("unreachable");
            }
        }
    }

    private static void threadBlock(String[] args) throws InterruptedException {
        int threads = intArg(args, 1, 100);
        Object lock = new Object();
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        synchronized (lock) {
            for (int i = 0; i < threads; i++) {
                pool.submit(() -> {
                    synchronized (lock) {
                        sleep(60_000);
                    }
                });
            }
            System.out.printf("submittedThreads=%d ownerThread=%s%n", threads, Thread.currentThread().getName());
            TimeUnit.MINUTES.sleep(10);
        }
    }

    private static void logMemory() {
        Runtime runtime = Runtime.getRuntime();
        long usedMb = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
        long maxMb = runtime.maxMemory() / 1024 / 1024;
        System.out.printf("heapUsedMb=%d heapMaxMb=%d%n", usedMb, maxMb);
    }

    private static int intArg(String[] args, int index, int fallback) {
        if (args.length <= index) {
            return fallback;
        }
        return Integer.parseInt(args[index]);
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    private static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }
}
