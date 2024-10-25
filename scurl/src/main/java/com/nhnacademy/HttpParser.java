package com.nhnacademy;

public class HttpParser {
    public static HttpRequest parseHttpRequest(String httpRequest, String host) {
        String[] lines = httpRequest.split("\\r?\\n");

        // 요청 라인 파싱
        String[] requestLineTokens = lines[0].split("\\s+");
        String method = requestLineTokens[0];
        String path = requestLineTokens[1];
        String version = requestLineTokens[2];
        HttpRequest request = new HttpRequest(host, method, path, version);

        // 헤더 파싱
        int i = 1;
        for (; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                break;
            }
            String[] headerTokens = lines[i].split(":\\s+");
            request.addHeader(headerTokens[0], headerTokens[1]);
        }

        // 바디 파싱
        StringBuilder bodyBuilder = new StringBuilder();
        for (i = i + 1; i < lines.length; i++) {
            bodyBuilder.append(lines[i]);
        }
        request.setBody(bodyBuilder.toString());

        return request;
    }

    public static HttpResponse parseHttpResponse(String httpResponse, boolean isPost) {
        String[] lines = httpResponse.split("\\r?\\n");

        // 상태 라인 파싱
        String[] statusLineTokens = lines[0].split("\\s+");
        String version = statusLineTokens[0];
        int statusCode = Integer.parseInt(statusLineTokens[1]);
        String reasonPhrase = statusLineTokens[2];
        HttpResponse response = new HttpResponse(version, statusCode, reasonPhrase);

        // 헤더 파싱
        int i = 1;
        for (; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                break;
            }
            String[] headerTokens = lines[i].split(":\\s+");
            response.addHeader(headerTokens[0], headerTokens[1]);
        }

        // 바디 파싱
        StringBuilder bodyBuilder = new StringBuilder();
        for (i = i + 1; i < lines.length; i++) {
            bodyBuilder.append(lines[i]);
        }
        response.setBody(bodyBuilder.toString());

        if (isPost) {
            String json = response.getBody();
            response.setJson(json);
        }

        return response;
    }
}
