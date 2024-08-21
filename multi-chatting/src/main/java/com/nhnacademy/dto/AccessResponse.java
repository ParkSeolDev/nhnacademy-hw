package com.nhnacademy.dto;

public class AccessResponse extends Response {
    private ResponseType response;

    public AccessResponse(int id, Type type, String client_id, ResponseType response) {
        super(id, type, client_id);
        this.response = response;
    }
    public ResponseType getResponse() {
        return response;
    } 
}
