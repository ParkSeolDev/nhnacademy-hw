package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void open(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
                Socket socket = serverSocket.accept();
                System.out.println("서버에 연결되었습니다.");
                System.out.println("Local address : " + socket.getLocalAddress().getHostAddress());
                System.out.println("Local port : " + socket.getLocalPort());
                System.out.println("Remote address : " + socket.getInetAddress().getHostAddress());
                System.out.println("Rmote port : " + socket.getPort());

                try {
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    while (!(line = bufferedReader.readLine()).equals("^C")) {
                        // socket.getOutputStream().write((line + "\n").getBytes());
                        System.out.println(line);
                    }
                } catch (IOException | NullPointerException ignore) {
                    
                }  finally {
                    socket.close();
                }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
