import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 具体执行下载任务的类,负责发送http请求,将数据下载到 buffer 中
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 14:53
 */
public class DownloadTask implements Runnable{
	private final long lowerBound ;
	private final long upperBound;
	private final DownloadBuffer xbuf;
	private final URL requestURL;
	private final AtomicBoolean cancelFlag;
	private final Tools tools = new Tools();

	public DownloadTask(long lowerBound, long upperBound, URL requestURL, Storage storage, AtomicBoolean cancelFlag) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.requestURL = requestURL;
		this.xbuf = new DownloadBuffer(lowerBound, upperBound, storage);
		this.cancelFlag = cancelFlag;
	}

	@Override
	public void run() {
		if(cancelFlag.get()){
			return;
		}
		ReadableByteChannel channel = null;
		try{
			channel = Channels.newChannel(issueRequest(requestURL,lowerBound,upperBound));
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while(!cancelFlag.get()&& channel.read(buf)>0){
				xbuf.write(buf);
				buf.clear();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			tools.silentClose(channel,xbuf);
		}
	}

	// 指定的 URL 发起 HTTP 分段下载请求
	private InputStream issueRequest(URL requestURL, long lowerBound, long upperBound) throws IOException {
		Thread me = Thread.currentThread();
		System.out.println(me+"->>["+lowerBound+","+upperBound+"]");
		final HttpURLConnection conn ;
		InputStream in = null ;
		conn = ((HttpURLConnection) requestURL.openConnection());

		String strConnTimeout = System.getProperty("x.dt.conn.timeout");
		int connTimeout = null == strConnTimeout ? 60000 : Integer.parseInt(strConnTimeout);
		conn.setConnectTimeout(connTimeout);

		String strReadTimeout = System.getProperty("x.dt.read.timeout");
		int readTimeout = null == strReadTimeout ? 60000 : Integer.parseInt(strReadTimeout);
		conn.setReadTimeout(readTimeout);
		
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Connection", "Keep-alive");
		conn.setRequestProperty("Range","bytes="+lowerBound+"-"+upperBound);
		conn.setDoOutput(true);
		conn.connect();
		
		int statusCode = conn.getResponseCode();
		if(HttpURLConnection.HTTP_PARTIAL!=statusCode){
			conn.disconnect();
			throw new IOException("Server exception,status code:" + statusCode);
		}
		System.out.println(me+"-Content-Range:"+conn.getHeaderField("Content-Range")+",Connection: "+conn.getHeaderField("Connection"));
		in = new BufferedInputStream(conn.getInputStream()){
			@Override
			public void close() throws IOException {
				try {
					super.close();
				} finally {
					conn.disconnect();
				}
			}
		};
		return in;
	}
}