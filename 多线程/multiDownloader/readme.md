多线程文件下载器

Main:
- main

BigFileDownloader:
- 取消下载
- 分发下载工作给 DownloadTask
- 清理
- 下载
- 报告下载进度
- 根据下载地址获取文件大小

DownloadBuffer:
- 关闭缓冲区
- 属性缓冲区
- 将缓冲区数据写入到文件中

DownloadTask:
- 指定的 URL 发起 HTTP 分段下载请求
- 执行并控制分段下载请求

Storage:
- 关闭
- 创建本地文件
- 获取写入到本地文件的字节数
- 将缓冲区中的数据写入到本地文件中

Tools:
- 关闭流