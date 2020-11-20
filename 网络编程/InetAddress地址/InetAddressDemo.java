 
import java.net.InetAddress;
import java.net.UnknownHostException;

/**InetAddress 类是Java包装用来表示IP地址的, 几乎所有的Java网络相关的类都和它有关
 * InetAddress.getByName(): 根据主机名获取IP地址,该方法会建立与本地DNS的一个连接，来获取名称和地址之间的映射关系。
 * https://www.cnblogs.com/blackiesong/p/6182022.html
 */
public class InetAddressDemo {
    
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("192.168.1.106");

        String name = address.getHostName();
        String ip = address.getHostAddress();
        System.out.println(name + "----" + ip);

    }
}
