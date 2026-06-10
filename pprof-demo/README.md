# Go pprof Demo

这是一个只依赖标准库的 `pprof` 练习项目。

## 启动

```bash
go run ./pprof-demo
```

服务默认监听 `http://localhost:6060`。

## 先制造负载

CPU：

```bash
curl "http://localhost:6060/work/cpu?seconds=15&workers=8"
```

内存：

```bash
curl "http://localhost:6060/work/heap?mb=128"
```

清理保留内存：

```bash
curl "http://localhost:6060/work/heap/clear"
```

锁竞争：

```bash
curl "http://localhost:6060/work/mutex?seconds=15&workers=32"
```

阻塞：

```bash
curl "http://localhost:6060/work/block?seconds=15&workers=32"
```

## 查看 profile

浏览器打开：

```text
http://localhost:6060/debug/pprof/
```

CPU 火焰图：

```bash
curl "http://localhost:6060/work/cpu?seconds=20&workers=8"
go tool pprof -http=:0 "http://localhost:6060/debug/pprof/profile?seconds=15"
```

Heap：

```bash
go tool pprof -http=:0 "http://localhost:6060/debug/pprof/heap"
```

Goroutine：

```bash
go tool pprof -http=:0 "http://localhost:6060/debug/pprof/goroutine"
```

Mutex：

```bash
curl "http://localhost:6060/work/mutex?seconds=15&workers=32"
go tool pprof -http=:0 "http://localhost:6060/debug/pprof/mutex"
```

Block：

```bash
curl "http://localhost:6060/work/block?seconds=15&workers=32"
go tool pprof -http=:0 "http://localhost:6060/debug/pprof/block"
```

## 终端里常用命令

进入交互模式：

```bash
go tool pprof "http://localhost:6060/debug/pprof/profile?seconds=15"
```

常用指令：

```text
top
list cpuWork
list nextPrime
web
quit
```

如果 `web` 不可用，通常是本机没有安装 Graphviz。macOS 可以用：

```bash
brew install graphviz
```
