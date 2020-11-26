import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class WebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8110);
        while(true){
            Socket socket= serverSocket.accept();
            new Thread(new MyHttpServer(socket)).start();
        }
    }
}

/**
 * 处理一个 Connection Socket 的请求的 HttpServer
 */
class MyHttpServer implements Runnable {
    private final String CRLF="\r\n";
    private Socket socket;
    private String webroot;
    public MyHttpServer(Socket socket) {
        System.out.println("连接到服务器的用户: "+socket);
        this.socket = socket;
        // 服务器的资源根目录是当前用户的工作目录
        webroot=System.getProperties().getProperty("user.dir");
    }

    /**
     * 目前只是实现了解析 HTTP 请求
     */
    @Override
    public void run() {
        BufferedReader reader=null;
        PrintWriter printWriter=null;
        try {
            // 读取输入
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 写输出
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            // 读入请求行
            String requestLine = reader.readLine();
            if(requestLine==null)return;
            // 请求行参数: POST/GET 请求uri HTTP版本
            String[] requstLineArgs = requestLine.split(" ");

            // 读入请求头
            String line = null;
            String[] keyValue = null;
            Map<String, String> requestHeaders = new HashMap<String, String>();
            while ((line = reader.readLine()) != null && !"".equals(line)) {
                int i = line.indexOf(": ");
                requestHeaders.put(line.substring(0,i),line.substring(i+2));
            }
            // 展示HTTP请求行中的参数
            System.out.println("用户"+socket.getPort()+"请求方法: "+requstLineArgs[0]);
            System.out.println("用户"+socket.getPort()+"请求地址: "+requstLineArgs[1]);
            System.out.println("用户"+socket.getPort()+"的HTTP版本: "+requstLineArgs[2]);
            // 展示Http 请求头中的参数
            System.out.println("********************");
            System.out.println("用户"+socket.getPort()+"请求头参数");
            showMapValue(requestHeaders);
            System.out.println("********************");
            
            // 如果是POST方法继续读入请求体
            if ("POST".equals(requstLineArgs[0])) {
                doPost(requstLineArgs, requestHeaders, reader, printWriter);
            }
            // 如果是GET方法就解析uri
            if ("GET".equals(requstLineArgs[0])) {
                doGet(requstLineArgs, requestHeaders, printWriter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if(socket!=null){
                try {
                    System.out.println("关闭与服务器连接的用户: "+socket);
                    System.out.println();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 请求行中是 GET 方法
     * @param requestLineArgs
     * @param requestHeaders
     * @param printWriter
     */
    public void doGet(String[] requestLineArgs,Map<String,String> requestHeaders,PrintWriter printWriter){
        // GET 的 requestLine 的解析
        int i = requestLineArgs[1].indexOf('?');
        // GET 有参数
        if(i!=-1) {
            // 将参数放入到 getArgs 中
            String sourceUri = requestLineArgs[1].substring(0, i);
            String[] s = requestLineArgs[1].substring(i + 1).split("&");
            String[] keyValue = null;
            Map<String, String> getArgs = new HashMap<String, String>();
            for (String ss : s) {
                String[] split = ss.split("=");
                getArgs.put(split[0], split[1]);
            }
            // 展示GET请求链接中的参数
            System.out.println("********************");
            System.out.println("用户"+socket.getPort()+"GET 中的参数");
            showMapValue(getArgs);
            System.out.println("********************");
            
            writeFile(printWriter,requestHeaders,sourceUri);
        }else{ // 没有额外参数, 属于直接访问
            writeFile(printWriter,requestHeaders,requestLineArgs[1]);
        }
    }

    /**
     * 请求行中是 POST 方法, 现在还不会处理POST方法, 先挖坑
     * @param requestLineArgs
     * @param requestHeaders
     * @param reader
     * @param printWriter
     */
    public void doPost(String[] requestLineArgs,Map<String,String> requestHeaders,BufferedReader reader,PrintWriter printWriter){
        String sourceUri=requestLineArgs[1];
        // 还需要读入 requestBody
    }

    /**
     * 将用户请求的文件发送给用户
     * @param printWriter
     * @param requestHeaders
     * @param uri
     */
    public void writeFile(PrintWriter printWriter,Map<String,String> requestHeaders,String uri) {
        String url=webroot+uri;
        System.out.println("用户"+socket.getPort()+"请求文件地址: "+url);
        File file = new File(url);
        try {
            String statusLine=null;
            String contentType=null;
            String entityBody=null;
            // 请求的文件存在
             if(file.exists()&&file.isFile()){
                statusLine ="HTTP/1.0 200 OK"+CRLF;
                contentType="Content-Type"  +contentType(file.toString())+CRLF;
                printWriter.print(statusLine);
                printWriter.print(contentType);
                printWriter.print(CRLF); //之前的为响应行+响应头部, 之后的是响应正文, 之间用 CRLF 分隔
                 // 将文件内容发送给用户
                 Scanner scanner = new Scanner(new FileInputStream(file));
                 while (scanner.hasNextLine()) {
                     printWriter.println(scanner.nextLine());
                 }
                 printWriter.flush();
             }
            else{                 
                statusLine="HTTP/1.0 404 Not Found"+CRLF;
                contentType="Content-Type: text/html"+CRLF;
                entityBody="<HTML>" +
                        "<HEAD><TITLE>Not Found</TITLE></HEAD>" +
                        "<BODY>Not Found</BODY></HTML>";
                 printWriter.print(statusLine);
                 printWriter.print(contentType);
                 printWriter.print(CRLF); //之前的为响应行+响应头部, 之后的是响应正文, 之间用 CRLF 分隔
                 printWriter.print(entityBody);
                 printWriter.flush();
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据用户请求的文件类型, 返回符合mime标准的文件类型
     * 目前只实现了请求html文件的功能
     * @param fileName
     * @return
     */
    public String contentType(String fileName){
        if(fileName.endsWith(".htm")||fileName.endsWith(".html")){
            return "text/html";
        }
        return "";
    }
    public void showMapValue(Map<String,String> map){
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
    }
}