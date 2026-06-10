# LeetCode Practice

这是一个极简 Go 刷题项目：每道题一个目录，每个目录一个可直接运行的 `main.go`。

## 目录约定

```text
problems/p0217/main.go
```

- `p0217` 表示题号 217，固定四位数，排序清楚。
- 每题都写成 `package main`，在 `main()` 里随手放几组样例数据。
- 不需要 `test` 文件夹；想 Debug 时直接给当前题的函数或 `main()` 打断点。

## 新增一道题

1. 新建目录：`problems/pXXXX`
2. 新建 `main.go`
3. 在 `main()` 里写样例输入并打印结果

运行示例：

```bash
go run ./problems/p0217
```

格式化：

```bash
gofmt -w problems/p0217/main.go
```
