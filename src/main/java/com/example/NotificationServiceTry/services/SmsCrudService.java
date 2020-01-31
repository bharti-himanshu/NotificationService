package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.data.dto.request.smsSend.GetSmsReq;
import com.example.NotificationServiceTry.data.dto.request.smsSend.SendSmsReq;
import com.example.NotificationServiceTry.data.entities.SmsRequest;
import com.example.NotificationServiceTry.repositories.SmsRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class SmsCrudService {
    @Autowired
    private  SmsRequestRepository smsRequestRepository;
    private static final Logger log = LoggerFactory.getLogger(SmsCrudService.class);

    public Integer createSms(SendSmsReq sendSmsReq){

        SmsRequest newSms = SmsRequest
                            .builder()
                            .phoneNumber(sendSmsReq.getPhoneNumber())
                            .message(sendSmsReq.getMessage())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
        smsRequestRepository.save(newSms);
        return newSms.getId();
    }

    public SmsRequest getSmsById(GetSmsReq getSmsReq){
        int id = getSmsReq.getRequestId();
        return smsRequestRepository.findById(id).get();
    }

    public ArrayList<SmsRequest> getAllSmsById(ArrayList<Integer> Ids){
        ArrayList<SmsRequest> allSms = (ArrayList<SmsRequest>) smsRequestRepository.findAllById(Ids);
        return allSms;
    }

    public void updateSms(SmsRequest sms_request){
        smsRequestRepository.save(sms_request);
    }
}
