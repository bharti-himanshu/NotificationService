package com.example.NotificationServiceTry.data.dto.response.smsSend;

import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsRequestRes {
    @JsonProperty("data")
    SmsRequest data;
}
