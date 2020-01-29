package com.example.NotificationServiceTry.dto;

import com.example.NotificationServiceTry.entities.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ElasticSearchResDto {

    Map<Integer, SmsRequest> data;
    String ScrollId;

    public ElasticSearchResDto(ScrollIdDto dto) {
        ArrayList<SmsRequest> smsList = dto.getData();
        ScrollId = dto.getScrollId();
        data = new HashMap<>();
        int i = 0;
        for (SmsRequest sms : smsList) {
            data.put(i++, sms);
        }
    }
}