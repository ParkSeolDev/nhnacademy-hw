package com.nhnacademy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhnacademy.dto.MessageResponse;
import com.nhnacademy.dto.Response;

public class Server {
    private List<UUID> clientIPList = new ArrayList<>();
    private Map<String, ChatClient> clients = new HashMap<>();
    private List<String> removeList = new ArrayList<>();
    private List<String> blackList = new ArrayList<>();
    private AtomicBoolean monitor = new AtomicBoolean(false);

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    public void open(int port) {
        LocalDate date = LocalDate.now();
        String logFilePath = "log/" + date + ".log";

        Thread settingCollector = new Thread(new SettingCollector());
        settingCollector.setDaemon(true);
        settingCollector.start();

        Thread inputAgent = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] commandArr = line.split(" ");
                    if (commandArr[0].equals("deny")) {
                        if (commandArr[1].equals("add")) {
                            addBlackList(commandArr[2]);
                        }
                        if (commandArr[1].equals("del")) {
                            deleteBlackList(commandArr[2]);
                        }
                    } else if (commandArr[0].equals("monitor")) {
                        if (commandArr[1].equals("on")) {
                            monitorOn();
                        }
                        if (commandArr[1].equals("off")) {
                            monitorOff();
                        }
                    } else if (commandArr[0].equals("send_off")) {
                        sendOff(commandArr[1]);
                    } else if (commandArr[0].equals("log")) {
                        if (commandArr[1].equals("show")) {
                            if (commandArr.length == 4) {
                                readLogLines(logFilePath, Integer.parseInt(commandArr[2]),
                                        Integer.parseInt(commandArr[3]));
                            } else if (commandArr.length == 3) {
                                readLogLines(logFilePath, 0, Integer.parseInt(commandArr[2]));
                            } else {
                                readLogLines(logFilePath, 0, 10);
                            }
                        }

                    } else if (commandArr[0].equals("client_list")) {
                        clientList();
                    }
                }
            } catch (Exception e) {

            }
        });

        Thread outputAgent = new Thread(() -> {
            try {
                while (!Thread.currentThread().interrupted()) {
                    
                    Response response = null;
                    String sender = null;
                    synchronized (clients) {
                        for (Map.Entry<String, ChatClient> chatClient : clients.entrySet()) {
                            if (!chatClient.getValue().isEmptyReceiveQueue()) {
                                sender = chatClient.getKey();
                                response = chatClient.getValue().receive();
                                if (response.getClient_id().equals("all")) {
                                    for (Map.Entry<String, ChatClient> chatClient2 : clients.entrySet()) {
                                        if (response instanceof MessageResponse) {
                                            chatClient2.getValue().socket.getOutputStream()
                                                    .write((JsonConverter.responseToJson(response, sender).toString()
                                                            + "\n").getBytes());
                                            chatClient2.getValue().socket.getOutputStream().flush();
                                        }

                                    }
                                } else {
                                    if (response instanceof MessageResponse) {
                                        clients.get(response.getClient_id()).socket.getOutputStream().write(
                                                (JsonConverter.responseToJson(response).toString() + "\n").getBytes());
                                        clients.get(response.getClient_id()).socket.getOutputStream().flush();
                                    }
                                }
                            }
                        }
                    }

                                if (monitor.get() && (response instanceof MessageResponse) && sender != null) {
                                    System.out.println(sender + " : " + ((MessageResponse) response).getMessage());
                                }
                            
                        
                        
                        sender = null;
                    
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        });


        inputAgent.start();
        outputAgent.start();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ChatClient chat = new ChatClient(serverSocket.accept());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(chat.socket.getInputStream()));

                    clientIPList.add(chat.getUuid());
                    while (!clients.containsValue(chat)) {
                        synchronized (clients) {
                            String clientId = reader.readLine();
                            if (clientId != null) {
                                synchronized (blackList) {
                                    for (String black : blackList) {
                                        if (clientId.equals(black)) {
                                            chat.socket.getOutputStream().write(("접속할 수 없습니다." + "\n").getBytes());
                                            chat.close();
                                            clientId = null;
                                        }
                                    }
                                }
                            }
                            if (clients.containsKey(clientId)) {
                                chat.socket.getOutputStream().write(("중복 로그인되었습니다." + "\n").getBytes());
                                chat.close();
                                clientId = null;
                            }
                            if (clientId != null) {
                                clients.put(clientId, chat);
                            }
                        }

                    }
                    Thread thread = new Thread(chat);
                    thread.start();
                    new Thread(() -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            synchronized (clients) {
                                synchronized (removeList) {
                                    for (Map.Entry<String, ChatClient> chatClient : clients.entrySet()) {
                                        if (chatClient.getValue().isClosed()
                                                || !chatClient.getValue().isConnected()
                                                || chatClient.getValue().isInterrupted()) {
                                            removeList.add(chatClient.getKey());
                                        }
                                    }

                                    for (String element : removeList) {
                                        synchronized (clientIPList) {
                                            clientIPList.remove(clients.get(element).getUuid());
                                        }
                                        clients.remove(element);
                                    }
                                    removeList.clear();
                                }
                            }
                        }

                    }).start();

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {

        }
    }

    public void readLogLines(String filePath, int s, int n) {
        Deque<String> deque = new ArrayDeque<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                deque.addLast(line);
                if (deque.size() > n + s) {
                    deque.removeFirst();
                }
            }
            for (int i = 0; i < s; i++) {
                deque.removeLast();
            }
        } catch (IOException e) {

        }
        while (!deque.isEmpty()) {
            System.out.println(deque.removeLast());
        }
    }
    
    public void monitorOn() {
        this.monitor.set(true);
        logger.trace("메시지 감시 설정");
    }

    public void monitorOff() {
        this.monitor.set(false);
        logger.trace("메시지 감시 해제");
    }

    public void sendOff(String id) {
        synchronized (clients) {
            try {
                clients.get(id).socket.getOutputStream().write(("강퇴당했습니다." + "\n").getBytes());
                clients.get(id).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (clientIPList) {
                clientIPList.remove(clients.get(id).getUuid());
            }
            clients.remove(id);
            logger.trace("강퇴 : {}", id);
        }
    }
    
    public void clientList() {
        synchronized (clients) {
            System.out.println(clients.keySet());
            logger.trace("접속 사용자 목록 : {}", clients.keySet());
        }
    }

    public void addBlackList(String id) {
        synchronized (blackList) {
            blackList.add(id);
            logger.trace("접속 차단 등록 : {}", id);
        }
    }

    public void deleteBlackList(String id) {
        synchronized (blackList) {
            blackList.remove(id);
            logger.trace("접속 차단 해제 : {}", id);
        }
    }
}
