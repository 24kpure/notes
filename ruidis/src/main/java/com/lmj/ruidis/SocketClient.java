package com.lmj.ruidis;

import com.lmj.ruidis.controller.CommandController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

/**
 * @Author: lmj
 * @Description:
 * @Date: Create in 9:44 下午 2020/3/8
 **/
public class SocketClient {
    public static final int port = 8080;
    public static final String host = "localhost";



    private static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Client Start...");
        while (true) {
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(host, port);


                //向服务器端发送数据
                PrintStream out = new PrintStream(socket.getOutputStream());
                int random = SocketClient.random.nextInt(10000);
                String command = "SET " + random + " " + random + "dxvalue";
                out.println(command);

                //读取服务器端数据
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String ret = input.readLine();
                System.out.println(command + "服务器端返回过来的是: " + ret);
                // 如接收到 "OK" 则断开连接
                if ("OK".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}