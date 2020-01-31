package com.example.NotificationServiceTry.data.dto.response.exceptionHandler;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class SendSmsFailureRes {
    Map<String,String> error;

    public SendSmsFailureRes(String message) {
        error = new LinkedHashMap<>();
        error.put("code","INVALID_REQUEST");
        error.put("message",message);
    }
}
