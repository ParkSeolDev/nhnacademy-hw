package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public void connect(String[] arr) {
        Scanner sc = new Scanner(System.in);
    
        try (Socket socket = new Socket(arr[0], Integer.parseInt(arr[1]))) {
            System.out.println("서버에 연결되었습니다.");

            while (true) {
                new Thread(() -> {
                    try {
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line1;
                        while ((line1 = input.readLine()) != null) {
                            System.out.println(line1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                String line = sc.nextLine();
                if (line.equals("^C")) {
                    Thread.currentThread().interrupt();
                    break;
                    
                } else {
                    socket.getOutputStream().write((line + '\n').getBytes());
                }
            }
            
        } catch (ConnectException e) {
            System.err.println(arr[0] + ":" + Integer.parseInt(arr[1]) + "에 연결할 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}