import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Send implements Runnable {
    private String from;  // 谁发的
    private String to;  // 发给谁
    private DatagramSocket socket;
    private int toPort;    
    public Send(String from, String to, DatagramSocket socket,int toPort) {
        this.from = from;
        this.to = to;
        this.socket = socket;
        this.toPort = toPort;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line=null;
        try {
            while (((line = reader.readLine())!=null)) {
                byte[] bytes=line.getBytes();
                socket.send(new DatagramPacket(bytes,bytes.length,InetAddress.getLocalHost(),toPort));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }
}
