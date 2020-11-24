import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Jerry {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        new Thread(new Receive("Tom","Jerry",new DatagramSocket(12000, InetAddress.getLocalHost()))).start();
        new Thread(new Send("Jerry","Tom",new DatagramSocket(),12001)).start();
        
    }
}
