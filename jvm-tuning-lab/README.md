# JVM Tuning Lab

这个项目用于面试准备：用可控的小程序复现线上 JVM 问题，再练习排查命令和参数调整。

## 环境

本机已安装：

- JDK 21: `/opt/homebrew/opt/openjdk@21`
- Maven 3.9.9

建议当前终端先固定 JDK 21：

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
export PATH="$JAVA_HOME/bin:$PATH"
```

## 构建

```bash
cd jvm-tuning-lab
./scripts/build.sh
```

## 场景

先启动一个场景，记住输出里的 `pid`，再用下面的命令观察。

### 1. 空进程基线

```bash
JVM_OPTS="-Xms128m -Xmx128m" ./scripts/run.sh idle
```

看 JVM 基本信息：

```bash
jcmd <pid> VM.version
jcmd <pid> VM.flags
jcmd <pid> VM.system_properties
jcmd <pid> GC.heap_info
```

### 2. 内存泄漏

```bash
JVM_OPTS="-Xms64m -Xmx64m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./heap.hprof" \
  ./scripts/run.sh heap-leak 2 300
```

排查顺序：

```bash
jstat -gcutil <pid> 1000
jcmd <pid> GC.heap_info
jcmd <pid> GC.class_histogram | head -30
jcmd <pid> GC.heap_dump ./heap-live.hprof
```

判断重点：

- Old 区持续上涨且 Full GC 后不下降，优先怀疑长生命周期引用。
- 只提高 `-Xmx` 不是修复，只是推迟 OOM。
- 面试回答要说清楚：先确认增长曲线，再拿 histogram/dump 定位对象和引用链。

常见参数：

```bash
-Xms512m -Xmx512m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/data/dumps/app.hprof
```

### 3. GC 频繁

```bash
JVM_OPTS="-Xms128m -Xmx128m -Xlog:gc*:file=gc.log:time,uptime,level,tags" \
  ./scripts/run.sh gc-churn 256
```

观察：

```bash
jstat -gcutil <pid> 1000
tail -f gc.log
```

判断重点：

- Young GC 很频繁但 Old 不涨，通常是分配速率高。
- Full GC 频繁且 Old 降不下来，方向变成内存泄漏或堆太小。
- 调参前先区分是 allocation rate 问题，还是 live set 太大。

常见参数：

```bash
-Xms2g -Xmx2g
-XX:MaxGCPauseMillis=200
-XX:+UseG1GC
-Xlog:gc*:file=/data/logs/gc.log:time,uptime,level,tags
```

### 4. CPU 飙高

```bash
./scripts/run.sh cpu-hot
```

排查顺序：

```bash
top -pid <pid>
jcmd <pid> Thread.print > threads.txt
```

如果需要把系统线程 ID 和 Java 线程对应起来：

```bash
ps -M <pid>
```

判断重点：

- 找到高 CPU 线程后，把线程 ID 转成十六进制，再在 thread dump 里找 `nid=0x...`。
- 关注线程栈顶是否在业务循环、锁竞争、序列化、正则、加解密或集合遍历。

### 5. 死锁

```bash
./scripts/run.sh deadlock
```

排查：

```bash
jcmd <pid> Thread.print
jstack <pid>
```

判断重点：

- `Found one Java-level deadlock` 是直接证据。
- 修复方向不是调 JVM 参数，而是统一加锁顺序、缩小锁范围或移除嵌套锁。

### 6. 线程堆积

```bash
./scripts/run.sh thread-block 200
```

排查：

```bash
jcmd <pid> Thread.print | grep -E 'java.lang.Thread.State|pool-|main'
jcmd <pid> VM.native_memory summary
```

如果 `VM.native_memory` 提示没有开启 NMT，重启时加：

```bash
-XX:NativeMemoryTracking=summary
```

判断重点：

- 大量 `BLOCKED` 看锁竞争。
- 大量 `WAITING/TIMED_WAITING` 看连接池、队列、下游超时。
- 线程太多会吃 native memory，不能只看 Java heap。

## 线上排查口径

面试里可以按这个顺序说：

1. 先保现场：记录时间、版本、机器、JVM 参数、GC 日志、线程 dump、heap histogram。
2. 看指标分叉：CPU、heap、GC 次数/耗时、线程数、RT、错误率。
3. 定位类型：CPU hot、内存泄漏、GC 频繁、死锁、线程池耗尽、native memory。
4. 小步调整：一次只改一个核心参数，保留回滚方案。
5. 验证结果：对比 P99、GC pause、Full GC 次数、Old 区水位、错误率。

## 常用参数速记

```bash
-Xms<size>                     初始堆大小
-Xmx<size>                     最大堆大小
-XX:+UseG1GC                   使用 G1，JDK 9+ 默认通常也是 G1
-XX:MaxGCPauseMillis=200       G1 期望暂停目标，不是硬保证
-Xlog:gc*:file=gc.log:time     JDK 9+ GC 日志
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/app.hprof
-XX:NativeMemoryTracking=summary
-XX:StartFlightRecording=filename=app.jfr,duration=120s,settings=profile
```

## 常用工具速记

```bash
jps -lv
jcmd <pid> VM.flags
jcmd <pid> GC.heap_info
jcmd <pid> GC.class_histogram
jcmd <pid> Thread.print
jstat -gcutil <pid> 1000
jmap -dump:live,format=b,file=heap.hprof <pid>
jstack <pid>
```
