// package com.nhnacademy;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.Socket;

// public class Client {
//     public void connect(String host, String method, int port) {
//         try (Socket socket = new Socket(host, port)) {
//             String version = "1.1";
//             String location = "/get";

//             HttpRequest httpRequest = new HttpRequest(host, method, location, version);

//             socket.getOutputStream().write(httpRequest.toString().getBytes());
//             socket.getOutputStream().flush();

//             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             String line;
//             StringBuilder response = new StringBuilder();
//             while ((line = reader.readLine()) != null) {
//                 response.append(line).append("\n");
//             }

//             HttpResponse httpResponse = HttpParser.parseHttpResponse(response.toString(), host);
//             System.out.println(httpResponse.getBody());

//             socket.getOutputStream().close();
//             reader.close();
//         } catch (IOException e) {
//             System.err.println(e.getMessage());
//         }
//     }
// }
