package com.example.NotificationServiceTry.dto;

import lombok.Data;

@Data
public class InvalidScrollIdDto {
    String error;

    public InvalidScrollIdDto() {
        error = "provided invalid ScrollId";
    }
}
