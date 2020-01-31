package com.example.NotificationServiceTry.controllers;


import com.example.NotificationServiceTry.data.dto.request.smsSend.GetSmsReq;
import com.example.NotificationServiceTry.data.dto.request.smsSend.SendSmsReq;
import com.example.NotificationServiceTry.data.dto.response.smsSend.SmsRequestRes;
import com.example.NotificationServiceTry.services.SmsCrudService;
import com.example.NotificationServiceTry.data.dto.response.smsSend.SmsCreateRes;
import com.example.NotificationServiceTry.data.entities.SmsRequest;
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
    public ResponseEntity<SmsCreateRes> createSmsRequest(@Valid @RequestBody SendSmsReq sendSmsReq){
            int requestId = smsCrudService.createSms(sendSmsReq);
            kafkaProducer.publishId(requestId);
            return new ResponseEntity<>(SmsCreateRes.builder().
                    data(SmsCreateRes.Data.builder().
                            requestId(String.valueOf(requestId))
                            .comments("Successfully Sent")
                            .build()
                        )
                    .build()
                    , HttpStatus.CREATED);
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<SmsRequestRes> getSmsFromRequestId(@RequestBody GetSmsReq getSmsReq){
            SmsRequest sms_request = smsCrudService.getSmsById(getSmsReq);
            return new ResponseEntity<>(new SmsRequestRes(sms_request),HttpStatus.OK);
    }

}
