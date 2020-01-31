package com.example.NotificationServiceTry.data.dto.request.searchSms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchSmsReq {
    String phoneNumber;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String scrollId;
    String text;
    String limit;

}
