package com.nhnacademy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceHandler implements Runnable {
    static final String CRLF = "\r\n";
    Thread thread;
    Socket socket;
    Logger log;

    public ServiceHandler(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
        log = LogManager.getLogger(this.getClass().getSimpleName());
    }

    String getFileList(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<Path> stream = Files.list(path)) {
            stream.map(p -> p.getFileName() + (Files.isDirectory(p) ? "/" : "")).forEach(name -> {
                builder.append(name).append(CRLF);
            });
            } catch (IOException ignore) {
            throw new InvalidStatusException(403, "Forbidden");
        }

        return builder.toString();
    }

    String getFile(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(x -> builder.append(x).append(CRLF));
        } catch (IOException e) {
            throw new InvalidStatusException(403, "Forbidden");
        }

        return builder.toString();
    }

    public Response process(Request request) {
        try {
            if (request.getMethod().equals("GET")) {
                Path relativePath = Paths.get("." + request.getPath());

                if (relativePath.normalize().equals(Paths.get(".."))) {
                    throw new InvalidStatusException(403, "Forbidden");
                }

                if (!Files.isReadable(relativePath)) {
                    throw new InvalidStatusException(403, "Forbidden");
                }

                if (!Files.exists(relativePath)) {
                    throw new InvalidStatusException(404, "Not Found");
                }

                Response response = new Response(request.getVersion(), 200, "OK");
                StringBuilder contentType = new StringBuilder();
                contentType.append("text");
                if (Files.isDirectory(relativePath)) {
                    contentType.append("; charset=utf-8");

                    response.setBody(getFileList(relativePath).getBytes(StandardCharsets.UTF_8));
                } else if (Files.isRegularFile(relativePath)) {
                    String filename = relativePath.getFileName().toString();
                    // if (filename.contains(".")) {
                    //     throw new UnknownContentTypeException();
                    // }

                    contentType.append("/")
                            .append(filename.substring(filename.lastIndexOf(".") + 1))
                            .append("; charset=utf-8");

                    response.setBody(getFile(relativePath).getBytes(StandardCharsets.UTF_8));
                }
                response.addField("content-type", contentType.toString());

                return response;
            }
            
            if (request.getMethod().equals("POST") && request.hasBody()) {
                Response response;
                if (request.getField("content-type").equals("application/xml")) {             
                    try (InputStream inputStream = socket.getInputStream()) {

                        Path filePath = Paths.get("upload", request.getPath().substring(1));

                        if (!Files.isWritable(filePath.getParent())) {
                            throw new InvalidStatusException(403, "Forbidden");
                        }

                        if (Files.exists(filePath)) {
                            throw new InvalidStatusException(409, "Conflict");
                        }

                        // Files.write(filePath, inputStream.readAllBytes());
                        Files.write(filePath, Arrays.toString(request.body).getBytes());

                        response = new Response(request.getVersion(), 201, "Created");
                    } catch (IOException e) {
                        if (e instanceof FileNotFoundException) {
                            response = new Response(request.getVersion(), 400, "Bad Request");
                        } else if (e instanceof AccessDeniedException) {
                            response = new Response(request.getVersion(), 403, "Forbidden");
                        } else {
                            response = new Response(request.getVersion(), 500, "Internal Server Error");
                        }
                    } catch (InvalidStatusException e) {
                        response = new Response(request.getVersion(), e.getCode(), e.getReason());
                    }
                } else {
                    response = new Response(request.getVersion(), 405, "Method Not Allowed");
                }
            }
            
            if (request.getMethod().equals("DELETE")) {
                Path filePath = Paths.get(request.getPath().substring(1));
                Response response;
                if (Files.exists(filePath)) {
                    try {
                        Files.delete(filePath);
                        response = new Response(request.getVersion(), 204, "No Content");
                    } catch (IOException e) {
                        e.printStackTrace();
                        response = new Response(request.getVersion(), 403, "Forbidden");
                    }

                } else {
                    response = new Response(request.getVersion(), 204, "No Content");
                }
                return response;
            }

            throw new InvalidStatusException(400, "Bad Request");
        } catch (InvalidStatusException e) {
            return new Response(request.getVersion(), e.getCode(), e.getReason());
        }
    }

    public void start() {
        thread.start();
    }

    @Override
    public void run() {
        log.trace("Start thread : {}", thread.getId());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream())) {
            while (!Thread.currentThread().isInterrupted()) {
                String requestLine = reader.readLine();
                if (requestLine == null) {
                    break;
                }

                String[] fields = requestLine.split("\\s", 3);
                if (fields.length != 3) {
                    throw new InvalidHttpRequestException();
                }

                Request request = new Request(fields[0], fields[1], fields[2]);

                String fieldLine;
                while ((fieldLine = reader.readLine()) != null) {
                    System.out.println(fieldLine);
                    if (fieldLine.length() == 0) {
                        break;
                    }
                    request.addField(fieldLine);
                }

                if (request.hasField(Request.FIELD_CONTENT_LENGTH)) {
                    char[] charArray = new char[request.getContentLength()]; // content-length만큼의 크기로 char[] 배열을 초기화합니다.
                    int totalBytesRead = 0;

                    // 한 글자씩 읽어서 char[]에 넣습니다.
                    while (totalBytesRead < charArray.length) {
                        int bytesRead = reader.read(charArray, totalBytesRead, charArray.length - totalBytesRead);
                        if (bytesRead == -1) {
                            break;
                        }
                        totalBytesRead += bytesRead;
                    }

                    // 읽은 데이터를 바디에 설정합니다.
                    request.setBody(charArray);

                    // char[] buffer = new char[request.getContentLength()];

                    // int bodyLength = reader.read(buffer, 0, request.getContentLength());
                    // if (bodyLength == request.getContentLength()) {
                    //     request.setBody(buffer);
                    // }
                }

                Response response = process(request);
                log.trace(response);

                writer.write(response.getBytes());
                writer.flush();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        log.trace("Finished thread : {}", thread.getId());
    }
}
