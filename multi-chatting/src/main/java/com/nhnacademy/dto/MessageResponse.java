package com.nhnacademy.dto;

public class MessageResponse extends Response {
    private String message;

    public MessageResponse(int id, Type type, String client_id, String message) {
        super(id, type, client_id);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
