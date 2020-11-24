import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class MyEmail {
    public static void main(String[] args) throws Exception {
        EMAILINFO emailInfo = new EMAILINFO();
        
        // 建立与邮件服务器的连接
        Socket socket = new Socket(emailInfo.smtpserver, emailInfo.smtpport);
        // 每次从服务器读入一行数据
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        //读入欢迎信息
        String response = responseReader.readLine();
        
        if(!response.startsWith(Integer.toString(Status.SERVICE_READY))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        OutputStream outputStream = socket.getOutputStream();
        // HELO 与服务器确认
        outputStream.write("HELO \r\n".getBytes("US-ASCII"));
        response = responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.OK))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        // 向服务器发出登录请求
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        String userNameUnEncoded= new String(encoder.encode(emailInfo.username.getBytes()))+"\r\n";
        String passwordUnEncoded=new String(encoder.encode(emailInfo.password.getBytes()))+"\r\n";
        
        outputStream.write("AUTH LOGIN \r\n".getBytes("US-ASCII"));
        
        
        response=responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.RETURN_OK))){
            throw new Exception(response);
        }else{
            String[] info = response.split("\\s+");
            System.out.println(info[0]+" "+new String(decoder.decode(info[1].getBytes())));
        }
        
        // 发送账号
        outputStream.write(userNameUnEncoded.getBytes("US-ASCII"));
        response=  responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.RETURN_OK))){
            throw new Exception(response);
        }else{
            String[] info = response.split("\\s+");
            System.out.println(info[0]+" "+new String(decoder.decode(info[1].getBytes())));
        }
        
        // 发送密码
        
        outputStream.write(passwordUnEncoded.getBytes("US-ASCII"));
        response=  responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.AUTH_SUCC))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        /*MAIL FROM 命令*/
        outputStream.write(("MAIL FROM: <"+emailInfo.from+"> \r\n").getBytes("ASCII"));
        response = responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.OK))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        /*RCPT TO 命令*/
        outputStream.write(("RCPT TO:<"+emailInfo.to+"> \r\n").getBytes("US-ASCII"));
        response = responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.OK))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        /*DATA 命令发送邮件*/
        outputStream.write("DATA \r\n".getBytes("ASCII"));
        response = responseReader.readLine();
        if(!response.startsWith(Integer.toString(Status.BEGIN_DATA))){
            throw new Exception(response);
        }else{
            System.out.println(response);
        }
        
        /*邮件头*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm:ss");
        String date= dateFormat.format(new Date());
        outputStream.write(("DATE: "+date+"\r\n").getBytes("US-ASCII"));
        outputStream.write(("From:"+emailInfo.from+"\r\n").getBytes());
        outputStream.write(("To:"+emailInfo.to+"\r\n").getBytes("US-ASCII"));
        
        /*邮件内容*/
//        BufferedReader bbji = new BufferedReader(new InputStreamReader(MyEmail.class.getClassLoader().getResourceAsStream(emailInfo.contentpath)));
//        String line=null;
//        while((line= bbji.readLine())!=null){
//            outputStream.write(line.getBytes("US-ASCII"));
//            System.out.println(line);
//        }
        outputStream.write(("mmmp \r\n").getBytes("US-ASCII"));
        outputStream.write("\r\n.\r\n".getBytes("US-ASCII"));
        outputStream.flush();
         // 响应内容
         response=responseReader.readLine();
         if(!response.startsWith(Integer.toString(Status.OK))){
             throw new Exception(response);
         }else{
             System.out.println(response);
         }
         
         // 退出系统
        outputStream.write("QUIT \r\n".getBytes("US-ASCII"));
        response = responseReader.readLine();
        System.out.println(response);
        outputStream.close();
    }
}
