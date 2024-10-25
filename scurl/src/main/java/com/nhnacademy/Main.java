package com.nhnacademy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        String method = "GET";
        String host = "";
        int port = 80;
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        boolean verboseOn = false;
        String version = "1.1";
        String location = "/get";
        String protocol = "http://";
        boolean isPost = false;
        int count = 0;
        
        Options options = new Options();

        options.addOption("v", "verbose", false, "요청, 응답 헤더를 출력한다.");
        options.addOption("H", "header", true, "임의의 헤더를 서버로 전송한다.");
        options.addOption("d", "data", true, "POST, PUT 등에 데이터를 전송한다.");
        options.addOption("X", "command", true, "사용할 method를 지정한다. 지정되지 않은 경우, 기본값은 GET");
        options.addOption("L", "location", false, "서버의 응답이 30x 계열이면 다음 응답을 따라 간다.");
        options.addOption("F", "file", true, "multipart/form-data를 구성하여 전송한다. content 부분에 @filename을 사용할 수 있다.");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        String url = cmd.getArgs()[0];

        Pattern pattern = Pattern.compile("(http[s]?):\\/\\/(.*?)(\\/.*?)");
        Matcher matcher = pattern.matcher(url);
        
        if (matcher.matches()) {
            protocol = matcher.group(1) != null ? matcher.group(1) : "http://";
            host = matcher.group(2);
            location = matcher.group(3) != null ? matcher.group(3) : "/";
        }

        try (Socket socket = new Socket(host, port)) {
            if (cmd.getArgs().length > 0) {
                method = location.substring(1).toUpperCase();

                switch (method) {
                    case "PUT":
                    case "POST":
                        isPost = true;
                        break;
                    case "DELETE":
                        break;
                    default:
                        method = "GET";
                        break;
                }

                if (cmd.hasOption("X")) {
                    method = cmd.getOptionValue("X");
                }

                if (cmd.hasOption("v")) {
                    verboseOn = true;
                }

                httpRequest = new HttpRequest(host, method, location, version);

                if (cmd.hasOption("H")) {
                    String[] hoption = cmd.getOptionValue("H").split(":");
                    httpRequest.addHeader(hoption[0], hoption[1]);
                }

                if (cmd.hasOption("d")) {
                    String data = cmd.getOptionValue("d");
                    httpRequest.addHeader("Content-Length", String.valueOf(data.length()));
                    httpRequest.setBody(data);
                }
                System.out.println(httpRequest.toString());

                socket.getOutputStream().write(httpRequest.toString().getBytes());
                socket.getOutputStream().flush();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()).length() != 0) {
                        response.append(line).append("\n");
                    }

                    httpResponse = HttpParser.parseHttpResponse(response.toString(), isPost);

                    if (verboseOn) {
                        System.out.println(httpResponse.getHeader());
                    }
                    System.out.println(httpResponse.getBody() + "\n");
                    
                    if (cmd.hasOption("L")) {
                        method = "GET";
                        while (String.valueOf(httpResponse.getStatusCode()).startsWith("30") && count < 6) {
                            count++;
                            location = httpResponse.getHeaderValue("location");
                            if (location == null) {
                                location = httpResponse.getHeaderValue("Location");
                            }
                            httpRequest = new HttpRequest(host, method, location, version);
                            socket.getOutputStream().write(httpRequest.toString().getBytes());
                            socket.getOutputStream().flush();

                            response = new StringBuilder();
                            while ((line = reader.readLine()) != null && (line).length() != 0) {
                                response.append(line).append("\n");
                            }
                            System.out.println(response);

                            httpResponse = HttpParser.parseHttpResponse(response.toString(), isPost);

                            if (verboseOn) {
                                System.out.println(httpResponse.getHeader());
                            }
                            System.out.println(httpResponse.getBody() +"\n");
                            
                            
                        }
                        if (count == 6) {
                            System.out.println("Error! Too many redirects");
                        }
                    }

                    socket.getOutputStream().close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}