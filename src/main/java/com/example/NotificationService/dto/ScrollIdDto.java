package com.example.NotificationServiceTry.dto;

import com.example.NotificationServiceTry.entities.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ScrollIdDto {
    String ScrollId;
    ArrayList<SmsRequest> data;

}
