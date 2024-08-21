package com.nhnacademy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.nhnacademy.dto.MessageRequest;
import com.nhnacademy.dto.Request;
import com.nhnacademy.dto.Response;
import com.nhnacademy.dto.Type;

public class ChatClient implements Runnable {
    private UUID uuid;
    Socket socket;
    Queue<Response> responseQueue = new LinkedList<>();
    Queue<Request> requestQueue = new LinkedList<>();
    private Thread thread;
    private AtomicInteger messageIndex = new AtomicInteger(0);
    private String clientId;

    public ChatClient(Socket socket) {
        this.socket = socket;
        this.uuid = UUID.randomUUID();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public boolean isEmptyReceiveQueue() {
        synchronized (responseQueue) {
            return responseQueue.isEmpty();
        }
    }

    public void send(String message) {
        if (message.equals("^C")) {
            close();
        }
        synchronized (requestQueue) {
            if (message.startsWith("/")) {
                String targetId = extractIDAndMessage(message)[0];
                requestQueue
                        .add(new MessageRequest(messageIndex.getAndIncrement(), Type.message, targetId, extractIDAndMessage(message)[1]));
            } else {
                requestQueue.add(new MessageRequest(messageIndex.getAndIncrement(), Type.message, "all", message));
            }
        }

    }
    
    private static String[] extractIDAndMessage(String str) {
        String[] result = new String[2];
        int idIndex = str.indexOf("/");

        if (idIndex != -1) {
            int spaceIndex = str.indexOf(" ", idIndex);
            if (spaceIndex != -1) {
                String id = str.substring(idIndex + 1, spaceIndex);
                String message = str.substring(spaceIndex + 1);
                result[0] = id;
                result[1] = message;
            }
        }
        return result;
    }

    public Response receive() {
        synchronized (responseQueue) {
            return responseQueue.poll();
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getIP() {
        return socket.getInetAddress().getHostAddress();
    }

    public int getPort() {
        return socket.getPort();
    }

    @Override
    public void run() {
        thread = Thread.currentThread();
        try (
            BufferedReader inputRemote = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter outputRemote = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            Thread receiver = new Thread(() -> {
                try {
                    String line;
                    while ((line = inputRemote.readLine()) != null) {
                        synchronized (responseQueue) {
                            responseQueue.add(JsonParser.parseJsonToMessageResponse(line));
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                } finally {
                    close();
                }
            });

            Thread sender = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        synchronized (requestQueue) {
                            if (!requestQueue.isEmpty()) {
                                outputRemote.write(JsonConverter.requestToJson(requestQueue.poll()).toString());
                                outputRemote.flush();
                                
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });

            receiver.start();
            sender.start();

            receiver.join();
            sender.interrupt();
            sender.join();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } catch (InterruptedException e) {
            thread.interrupt();
        } finally {
            close();
        }
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (thread != null) {
                thread.interrupt();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isInterrupted() {
        if (thread != null) {
            return thread.isInterrupted();
        }
        return false;
    }
}
