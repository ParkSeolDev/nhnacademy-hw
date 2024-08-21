package com.nhnacademy;

import org.json.JSONObject;

import com.nhnacademy.dto.MessageResponse;
import com.nhnacademy.dto.Type;

public class JsonParser {
    public static MessageResponse parseJsonToMessageResponse(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        int id = jsonObject.getInt("id");
        String type = jsonObject.getString("type");
        String clientId = jsonObject.getString("client_id");
        String message = jsonObject.getString("message");
        return new MessageResponse(id, Type.valueOf(type), clientId, message);
    }
}
