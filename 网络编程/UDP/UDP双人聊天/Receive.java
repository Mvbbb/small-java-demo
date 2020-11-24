import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receive implements Runnable{
    private String from;  // 来自谁的
    private String to; // 谁收到了
    private DatagramSocket socket;

    public Receive(String from, String to, DatagramSocket socket) {
        this.from = from;
        this.to = to;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        System.out.println("你好: "+to+" ,"+"你正在和 "+from+" 聊天");
        while(true){
            try {
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
                socket.receive(packet);
                System.out.println(from+": "+new String(bytes,0, packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
