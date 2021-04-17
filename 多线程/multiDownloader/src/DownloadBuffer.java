import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 下载缓冲区.
 * 下载线程每次从网络读取的数据都是先被写入缓冲区,只有当这个缓冲区满的时候其中的内容才会被写入本地文件
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 14:55
 */
public class DownloadBuffer implements Closeable {
	public final ByteBuffer byteBuf;
	public final Storage storage;
	// 当前 Buffer 中缓冲的数据相对于整个存储文件的位置移动
	private long globalOffset ;
	private long upperBound;
	private int offset = 0;
	
	public DownloadBuffer(long globalOffset, long upperBound,
	                      final Storage storage) {
		this.globalOffset = globalOffset;
		this.upperBound = upperBound;
		this.byteBuf = ByteBuffer.allocate(1024 * 1024);
		this.storage = storage;
	}
	public void write(ByteBuffer buf) throws IOException {
		int length = buf.position();
		final int capacity = byteBuf.capacity();
		// 当前缓冲区已满，或者剩余容量不够容纳新数据
		if (offset + length > capacity || length == capacity) {
			// 将缓冲区中的数据写入文件
			flush();
		}
		byteBuf.position(offset);
		buf.flip();
		byteBuf.put(buf);
		offset += length;
	}

	/**
	 * 将缓冲区中的数据持久化到文件中
	 * @throws IOException
	 */
	public void flush() throws IOException {
		int length ;
		byteBuf.flip();
		length = storage.store(globalOffset, byteBuf);
		byteBuf.clear();
		globalOffset += length;
		offset = 0;
	}
	
	@Override
	public void close() throws IOException {
		System.out.printf("globalOffset:%s,upperBound:%s\n",globalOffset,upperBound);
		if(globalOffset<upperBound){
			flush();
		}
	}
}
