import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Tom {
    public static void main(String[] args) throws UnknownHostException, SocketException {        
        new Thread(new Receive("Jerry","Tom",new DatagramSocket(12001,InetAddress.getLocalHost()))).start();
        new Thread(new Send("Tom","Jerry",new DatagramSocket(),12000)).start();
    }
}
