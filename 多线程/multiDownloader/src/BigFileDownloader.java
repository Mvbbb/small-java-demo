import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 大文件下载器
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 14:15
 */
public class BigFileDownloader {
	protected final URL requestURL;
	protected final long fileSize;
	protected final Storage storage;
	// 任务是否结束
	protected final AtomicBoolean taskCanceled = new AtomicBoolean(false); 

	public BigFileDownloader(String strURL) throws Exception {
		requestURL = new URL(strURL);
		// 获取下载资源的大小
		fileSize = retieveFileSize(requestURL);
		System.out.println("文件大小为: "+fileSize);
		String fileName = strURL.substring(strURL.lastIndexOf('/')+1);
		storage = new Storage(fileSize,fileName);
	}
	
	// 指定 URL 获取文件大小
	private static long retieveFileSize(URL requestURL) throws Exception {
		HttpURLConnection conn = null;
		long size = -1;
		try{
			conn = ((HttpURLConnection) requestURL.openConnection());
			conn.setRequestProperty("Connection","Keep-alive");
			conn.connect();
			int statusCode = conn.getResponseCode();
			if(HttpURLConnection.HTTP_OK != statusCode){
				throw new Exception("Server exception,status code: "+statusCode);
			}
			String cl = conn.getHeaderField("Content-Length");
			size = Long.parseLong(cl);
		}finally {
			if(conn!=null){
				conn.disconnect();
			}
		}
		return size;
	}

	/**
	 * 下载指定的文件
	 * @param taskCount 任务个数
	 * @param reportInterval 下载进度报告周期
	 */
	public void download(int taskCount,long reportInterval) throws InterruptedException {
		long chunkSizePerThread = fileSize/taskCount;
		// 下载数据段的开始字节
		long lowerBound = 0;
		// 下载数据段的结束字节
		long upperBound = 0;
		DownloadTask dt;
		for (int i= taskCount - 1; i >= 0; i--){
			lowerBound = i*chunkSizePerThread;
			if(i==taskCount-1){
				upperBound=fileSize;
			} else{
				upperBound = lowerBound + chunkSizePerThread -1;
			}
			// 创建下载任务
			dt = new DownloadTask(lowerBound,upperBound,requestURL,storage,taskCanceled);
			dispatchWork(dt,i);
		}
		// 定时下载报告
		reportProgess(reportInterval);
		// 清理程序占用的资源
		doCleanUp();
	}

	private void reportProgess(long reportInterval) throws InterruptedException {
		float lastCompletion;
		int completion = 0;
		while (!taskCanceled.get()) {
			lastCompletion = completion;
			completion = (int) (storage.getTotalWrites() * 100 / fileSize);
			if (completion == 100) {
				break;
			} else if (completion - lastCompletion >= 1) {
				System.out.printf("Completion:%s%%\n", completion);
				if (completion >= 90) {
					reportInterval = 1000;
				}
			}
			Thread.sleep(reportInterval);
		}
		System.out.printf("Completion:%s%%\n", completion);
	}

	protected void doCleanUp() {
		// 如果成功将 exceptValue 修改为 newValue 就返回 true
	    if(taskCanceled.compareAndSet(false,true)){
	    	doCleanUp();
	    }
	}
	
	protected void cancelDownload(){
		if(taskCanceled.compareAndSet(false,true)){
			doCleanUp();
		}
	}
	
	protected void dispatchWork(final DownloadTask dt, final int workIndex){
		Thread thread = new Thread(()->{
			try{
				dt.run();
			} catch (Exception e){
				e.printStackTrace();
				cancelDownload();
			}
		});
		thread.setName("downloader-"+workIndex);
		thread.start();
	}
}