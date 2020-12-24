/**
 * 
 */
public class Proxy {
    public static void main(String[] args) {
        Image image = new ProxyImage("test_10mb.png");
        // 第一次是new的，图像从磁盘加载
        image.display();
        // 第二次取缓存，图像不需要从磁盘加载
        image.display();
    }
}

interface Image {
    void display();
}

/**
 * 真正访问文件的类
 */
class RealImage implements Image {
	
    private String fileName;
   
    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(fileName);
    }
   
    private void loadFromDisk(String fileName) {
        Log.e("RealImage", "loading " + fileName);
    }
   
    @Override
    public void display() {
        Log.e("RealImage", "Displaying " + fileName);
    }
}

/**
 * 代理类
 */
class ProxyImage implements Image {
	
    private String fileName;
    private RealImage realImage;
   
    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }
   
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}