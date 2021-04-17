import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 文件存储类
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 14:16
 */
public class Storage implements Closeable,AutoCloseable {
	protected final AtomicLong totalWrites = new AtomicLong(0);
	private final RandomAccessFile storeFile;
	private final FileChannel storeChannel;
	private final Tools tools = new Tools();

	public Storage(long fileSize,String fileShortName) throws IOException {
		String fullFileName = System.getProperty("user.dir")+"/"+fileShortName;
		String localFileName;
		localFileName = createStoreFile(fileSize,fullFileName);
		storeFile = new RandomAccessFile(localFileName,"rw");
		storeChannel = storeFile.getChannel();
	}

	/**
	 * 将byteBuf中指定的数据写入文件
	 *
	 * @param offset  写入数据在整个文件中的起始偏移位置
	 * @param byteBuf byteBuf必须在该方法调用前执行byteBuf.flip()
	 * @return 写入文件的数据长度
	 * @throws IOException
	 */
	public int store(long offset, ByteBuffer byteBuf)
			throws IOException {
		int length;
		storeChannel.write(byteBuf, offset);
		length = byteBuf.limit();
		totalWrites.addAndGet(length);
		return length;
	}

	public long getTotalWrites() {
		return totalWrites.get();
	}

	private String createStoreFile(final long fileSize, String fullFileName) throws IOException {
		File file = new File(fullFileName);
		System.out.println("创建本地文件: "+fullFileName);
		RandomAccessFile raf;
		raf = new RandomAccessFile(file, "rw");
		try{
			raf.setLength(fileSize);
		}finally {
			tools.silentClose(raf);
		}
		return fullFileName;
	}
	


	@Override
	public void close() throws IOException {
		if(storeChannel.isOpen()){
			tools.silentClose(storeChannel,storeFile);
		}
	}
}