package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.dto.SmsCreateDto;
import com.example.NotificationServiceTry.entities.SmsRequest;
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

    public Integer createSms(SmsCreateDto smsCreateDtoDto){

        SmsRequest newSms = SmsRequest
                            .builder()
                            .phone_number(smsCreateDtoDto.getPhone_number())
                            .message(smsCreateDtoDto.getMessage())
                            .created_at(LocalDateTime.now())
                            .updated_at(LocalDateTime.now())
                            .build();
        smsRequestRepository.save(newSms);
        return newSms.getId();
    }

    public SmsRequest getSmsById(int id){
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
