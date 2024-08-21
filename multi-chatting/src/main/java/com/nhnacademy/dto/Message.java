package com.nhnacademy.dto;

public class Message {
    private int messageId;
    private Type type;
    private String content;
    private String sender;
    private String receiver;

    public Message(int messageId, Type type, String content, String sender) {
        this.messageId = messageId;
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.receiver = "all";
    }

    public Message(int messageId, Type type, String content, String sender, String receiver) {
        this.messageId = messageId;
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getMessageId() {
        return messageId;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
