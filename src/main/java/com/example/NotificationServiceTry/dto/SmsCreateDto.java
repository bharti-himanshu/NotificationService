package com.example.NotificationServiceTry.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class SmsCreateDto {
    @NotEmpty
    private String phone_number;
    private String message;

    public SmsCreateDto(String phone_number, String message){
        this.phone_number = phone_number;
        this.message = message;
    }
}
