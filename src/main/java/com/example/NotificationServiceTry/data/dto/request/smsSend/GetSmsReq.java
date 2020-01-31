package com.example.NotificationServiceTry.data.dto.request.smsSend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSmsReq {
    int requestId;
}
