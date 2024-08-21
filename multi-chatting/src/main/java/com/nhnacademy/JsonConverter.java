package com.nhnacademy;

import org.json.JSONObject;

import com.nhnacademy.dto.AccessResponse;
import com.nhnacademy.dto.MessageRequest;
import com.nhnacademy.dto.MessageResponse;
import com.nhnacademy.dto.Request;
import com.nhnacademy.dto.Response;

public class JsonConverter {
    public static JSONObject requestToJson(Request request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", request.getId());
        jsonObject.put("type", request.getType().toString());
        jsonObject.put("client_id", request.getClient_id());
        if (request instanceof MessageRequest) {
            jsonObject.put("message", ((MessageRequest) request).getMessage());
        }
        return jsonObject;
    }
    
    public static JSONObject responseToJson(Response response) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", response.getId());
        jsonObject.put("client_id", response.getClient_id());
        jsonObject.put("type", response.getType().toString());
        if (response instanceof MessageResponse) {
            jsonObject.put("message", ((MessageResponse) response).getMessage());
        } else {
            jsonObject.put("response", ((AccessResponse) response).getResponse());
        }
        return jsonObject;
    }
    
    public static JSONObject responseToJson(Response response, String sender) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", response.getId());
        jsonObject.put("client_id", sender);
        jsonObject.put("type", response.getType().toString());
        if (response instanceof MessageResponse) {
            jsonObject.put("message", ((MessageResponse)response).getMessage());
        } else {
            jsonObject.put("response", ((AccessResponse)response).getResponse());
        }
        return jsonObject;
    }
}
