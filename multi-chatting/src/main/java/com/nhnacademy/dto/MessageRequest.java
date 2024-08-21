package com.nhnacademy.dto;

public class MessageRequest extends Request {
    private String message;

    public MessageRequest(int id, Type type, String target_id, String message) {
        super(id, type, target_id);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
}
