import java.io.Closeable;

/**
 * 工具类
 * @program: multiDownloader
 * @description:
 * @author: Mvbbb
 * @create: 2021-04-17 15:00
 */
public class Tools {
	// 关闭流
	public static void silentClose(Closeable... closeable){
		if(closeable == null){
			return;
		}
		for (Closeable c : closeable) {
			if(c==null){
				continue;
			}
			try{
				c.close();
			}catch (Exception e){}
		}
	}
}