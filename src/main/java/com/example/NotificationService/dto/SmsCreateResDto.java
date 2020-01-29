package com.example.NotificationService.dto;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class SmsCreateResDto {
    Map<String,String> data;

    public SmsCreateResDto(Integer RequestId) {
        data = new LinkedHashMap<>();
        data.put("request_id",RequestId.toString());
        data.put("comments","Successfully Sent");
    }
}
