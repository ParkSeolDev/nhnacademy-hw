package com.nhnacademy.dto;

public class Response {
    private int id;
    private Type type;
    private String client_id;

    public Response(int id, Type type, String client_id) {
        this.id = id;
        this.type = type;
        this.client_id = client_id;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getClient_id() {
        return client_id;
    }
}
