package com.nhnacademy;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class HttpResponse {
    private String version;
    private int statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private String body;
    private JSONObject json;

    public HttpResponse(String version, int statusCode, String reasonPhrase) {
        this.version = version;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = new HashMap<>();
        this.body = "";
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }
    
    public String getHeaderValue(String key) {
        return headers.get(key);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getHeader() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(version + " " + statusCode + " " + reasonPhrase + "\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            jsonBuilder.append(entry.getKey() + ": " + entry.getValue() + "\r\n");
        }
        return jsonBuilder.toString();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setJson(String data) {
        JSONObject jsonObject = new JSONObject(data);
        JSONObject jsonValue = jsonObject.getJSONObject("json");
        this.json = jsonValue;
    }

    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\r\n");
        jsonBuilder.append("\"version\": \"" + version + "\",\r\n");
        jsonBuilder.append("\"statusCode\": " + statusCode + ",\r\n");
        jsonBuilder.append("\"reasonPhrase\": \"" + reasonPhrase + "\",\r\n");
        jsonBuilder.append("\"headers\": {\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            jsonBuilder.append("\"" + entry.getKey() + "\": \"" + entry.getValue() + "\",\r\n");
        }
        jsonBuilder.append("},\r\n");
        jsonBuilder.append("\"body\": \"" + body + "\r\n");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
