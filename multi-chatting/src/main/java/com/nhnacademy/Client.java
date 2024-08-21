package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import com.nhnacademy.dto.MessageResponse;
import com.nhnacademy.dto.Response;

public class Client {
    private Scanner sc = new Scanner(System.in);
    private String clientId;
    private boolean clientRunning = true;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void connect(String[] arr) {
        
        try (Socket socket = new Socket(arr[0], Integer.parseInt(arr[1]))) {
            clientId = sc.nextLine();
            setClientId(clientId);
            socket.getOutputStream().write((clientId + '\n').getBytes());

            ChatClient chatClient = new ChatClient(socket);
            chatClient.setClientId(clientId);
            Thread thread = new Thread(chatClient);
            thread.start();

            Thread inputAgent = new Thread(() -> {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
                    String line;
                    while ((line = input.readLine()) != null && clientRunning) {
                        if (line.equals("^C")) {
                            chatClient.send(line + "\n");
                            break;
                        } else {
                            chatClient.send(line + "\n");
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            
            });

            Thread outputAgent = new Thread(() -> {
                while (clientRunning && !thread.isInterrupted()) {
                    if (!chatClient.isEmptyReceiveQueue()) {
                        Response response = chatClient.receive();
                        System.out.println(((MessageResponse)response).getMessage());
                    }
                }
            });

            inputAgent.start();
            outputAgent.start();

            thread.join();
            clientRunning = false;
        } catch (InterruptedException | IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
