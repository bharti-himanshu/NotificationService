package com.example.NotificationServiceTry.data.dto.response.searchSms;

import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
public class SearchSmsRes {

    @JsonProperty("Scroll_Id")
    String scrollId;
    @JsonProperty
    ArrayList<SmsRequest> data;

}
