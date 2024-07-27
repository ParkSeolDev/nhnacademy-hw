package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

interface Message {
    void onMessageReceived(String message);
}

public class Server {

    public void open(int port) {
        Scanner sc = new Scanner(System.in);
        Board board = new Board();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket1 = serverSocket.accept();
            socket1.getOutputStream().write(("사용자1이 입장하였습니다." + '\n').getBytes());
            Socket socket2 = serverSocket.accept();
            socket1.getOutputStream().write(("사용자2이 입장하였습니다." + '\n').getBytes());
            socket2.getOutputStream().write(("사용자2이 입장하였습니다." + '\n').getBytes());

            socket1.getOutputStream().write(("몇 줄로 생성하시겠어요?" + '\n').getBytes());

            Message message = new Message() {
                @Override
                public void onMessageReceived(String message) {
                    board.setBoard(Integer.parseInt(message));
                }
            };

            receiveMessage(socket1, message);

            if (board.getN() != 0) {
                for (int turn = 0; turn < board.getN() * board.getN(); turn++) {
                    int player = (turn % 2 == 0) ? 1 : 2;

                    if (player == 1) {
                        socket1.getOutputStream().write(("번호를 고르세요." + '\n').getBytes());
                        receiveMessage(socket1, message = new Message() {
                            @Override
                            public void onMessageReceived(String message) {
                                board.initNumber(message, player);
                            }
                        });
                    } else {
                        socket2.getOutputStream().write(("번호를 고르세요." + '\n').getBytes());
                        receiveMessage(socket2, message = new Message() {
                            @Override
                            public void onMessageReceived(String message) {
                                board.initNumber(message, player);
                            }
                        });
                    }
                }

                socket1.getOutputStream().write(("------------------"+"\n").getBytes());
                socket2.getOutputStream().write(("------------------"+"\n").getBytes());

                for (int i = 0; i < board.getN() * board.getN(); i++) {
                    socket1.getOutputStream().write(board.toString().getBytes());
                    socket2.getOutputStream().write(board.toString().getBytes());

                    socket1.getOutputStream().write(("번호를 고르세요." + '\n').getBytes());

                    receiveMessage(socket1, message = new Message() {
                        @Override
                        public void onMessageReceived(String message) {
                            board.selectNumber(message, 1);
                        }
                    });
                    endGame(board, socket1, socket2);

                    socket1.getOutputStream().write(board.toString().getBytes());
                    socket2.getOutputStream().write(board.toString().getBytes());

                    socket2.getOutputStream().write(("번호를 고르세요." + '\n').getBytes());

                    receiveMessage(socket2, new Message() {
                        @Override
                        public void onMessageReceived(String message) {
                            board.selectNumber(message, 2);
                        }
                    });
                    endGame(board, socket1, socket2);
                }
            }

        } catch (ConnectException e) {
            System.err.println(port + "에 연결할 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
    
    private boolean endGame(Board board, Socket socket1, Socket socket2) throws IOException {
        int winner = board.checkBingo();
        if (winner != 0) {
            socket1.getOutputStream().write(("사용자 " + winner + " 승리" + '\n').getBytes());
            socket2.getOutputStream().write(("사용자 " + winner + " 승리" + '\n').getBytes());
            socket1.close();
            socket2.close();
            System.exit(0);
        }
        return false;
    }

    private void receiveMessage(Socket socket, Message message) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            if ((line = input.readLine()) != null) {
                message.onMessageReceived(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleUserInput(Scanner sc, Socket socket) {
        try {
            String line;
            while (!(line = sc.nextLine()).equals("^C")) {
                socket.getOutputStream().write((line+'\n').getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}