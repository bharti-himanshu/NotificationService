package com.example.NotificationServiceTry.data.dto.request.smsSend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsReq {
    @NotEmpty
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String message;

}
