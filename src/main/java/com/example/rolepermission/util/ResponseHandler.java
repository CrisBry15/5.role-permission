package com.example.rolepermission.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, int statusCode, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", statusCode);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.valueOf(statusCode));
    }
}