/**
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 14:13
 */
public class Main {
	public static void main(String[] args) throws Exception{
		// 下载线程数
		int workerThreadsCount = 2;
		// 报告周期
		long reportInterval = 3;
		// 下载地址
		String url = "https://cc-download.edrawsoft.cn/inst/mindmaster_cn_setup_full5375.exe";
		System.out.printf("downloading %s%nConfig:worker threads:%s,reportInterval:%s s.\n",url,workerThreadsCount,reportInterval);
		BigFileDownloader downloader = new BigFileDownloader(url);
		downloader.download(workerThreadsCount,reportInterval*1000);
	}
}