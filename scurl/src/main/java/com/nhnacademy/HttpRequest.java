package com.nhnacademy;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String host;
    private String method;
    private String location;
    private String version;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(String host, String method, String location, String version) {
        this.host = host;
        this.method = method;
        this.location = location;
        this.version = version;
        this.headers = new HashMap<>();
        this.body = null;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.method).append(" ").append(this.location).append(" HTTP/").append(this.version).append("\r\n");
        sb.append("Host: ").append(this.host).append("\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        sb.append("User-Agent: curl/7.79.1\r\n");
        if (!this.location.contains("30")) {
            sb.append("Connection: close\r\n");
        }
        sb.append("Accept: */*\r\n");
        sb.append("\r\n");
        if (body != null)
            sb.append(this.body).append("\r\n");
        return sb.toString();
    }
}
