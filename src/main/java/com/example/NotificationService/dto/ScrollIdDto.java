package com.example.NotificationService.dto;

import com.example.NotificationService.entities.SmsRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ScrollIdDto {
    String ScrollId;
    ArrayList<SmsRequest> data;

}
