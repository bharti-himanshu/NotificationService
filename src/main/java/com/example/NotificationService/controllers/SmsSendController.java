package com.example.NotificationServiceTry.controllers;


import com.example.NotificationServiceTry.dto.SmsCreateDto;
import com.example.NotificationServiceTry.dto.SmsRequestResDto;
import com.example.NotificationServiceTry.services.SmsCrudService;
import com.example.NotificationServiceTry.dto.SmsCreateResDto;
import com.example.NotificationServiceTry.entities.SmsRequest;
import com.example.NotificationServiceTry.services.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/sms")
public class SmsSendController {
    private static final Logger log = LoggerFactory.getLogger(SmsSendController.class);

    @Autowired
    private SmsCrudService smsCrudService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    Jedis jedis;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SmsCreateResDto> createSmsRequest(@Valid @RequestBody SmsCreateDto smsCreateDto){
            int requestId = smsCrudService.createSms(smsCreateDto);
            kafkaProducer.publishId(requestId);
            return new ResponseEntity<>(new SmsCreateResDto(requestId), HttpStatus.CREATED);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<SmsRequestResDto> getSmsFromRequestId(@PathVariable("requestId") int requestId){
            SmsRequest sms_request = smsCrudService.getSmsById(requestId);
            return new ResponseEntity<>(new SmsRequestResDto(sms_request),HttpStatus.OK);
    }

}