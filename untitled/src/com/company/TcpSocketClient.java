package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpSocketClient {

    public static void client()  {
        String line="";
        String fileName = "C:\\Users\\Ject\\Documents\\output.prn";

        try (Scanner sc = new Scanner(new FileReader(fileName))) {
            while (sc.hasNextLine()) {  //按行读取字符串
                 line += sc.nextLine();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(line);

        try {
            // 和服务器创建连接
            Socket socket = new Socket("192.168.50.38", 2345);
            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            //pw.write("状态已改变");

            StringBuilder St1 = new StringBuilder();
            St1.append(line.toString());
//            St1.append("\u0002\u001BA\u001BZ\u0003");
//            St1.append("\u0002\u001BA\u001BA1V00361H1251\u001BZ\u0003");
//            St1.append("\u0002\u001BA\u001BA3V+000H+000\u001BZ\u0003");
//            St1.append("\u0002\u001BA\u001B%2\u001BH716\u001BV199");
//            St1.append("\u001BP0\u001BPS\u001BCEUTF-8\u001BRG0,6,0,76,76,123");
//            St1.append("\u001BQ1");
//            St1.append("\u001BZ\u0003");
            pw.write(St1.toString());
            //flush方法是用于将输出流缓冲的数据全部写到目的地。
            //所以一定要在关闭close之前进行flush处理，即使PrintWriter有自动的flush清空功能
            pw.flush();
            Thread.sleep(5000);
            socket.shutdownOutput();
            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;

            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器返回信息：" + info);
            }

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
