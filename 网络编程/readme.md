这个目录下主要包含了计算机网络和Java结合的相关内容. 

# TCP/UDP

## UDP

### udp发送端

1. 创建发送端的Socket对象 `new DatagramSocket();`

2. 数据打包, 需要指定字节数组, 字节数组长度, 目标主机, 目标端口号`new DatagramPacket(bys, bys.length, InetAddress.getByName("192.168.1.106"), 12345);`

3. 发送数据`socket.send(packet)`

4. 关闭 Socket

### udp接收端

1. 创建Socket对象, 需要指定端口号`new DatagramSocket(12345);`

2. 构造接收 Packet, `new DatagramPacket(bys,bys.length);`

3. 接收数据 `socket.receive(packet)`

4. 关闭`socket`

## TCP

### tcp发送端

1. 创建发送端的Socket `new Socket("192.168.1.106",8888);`

2. 获取 Socket 输出流, 写字节流

3. 关闭 Socket

### tcp接收端  

1. 创建接收端 Welcome Socket, 指定端口号`new ServerSocket(8888);`

2. 通过 Welcome Socket 获取 Connection Socket, 使用 Connection Socket 进行交互

3. 获取 Connection Socket 输入流, 使用字节数组接收

4. 关闭 Connection Socket