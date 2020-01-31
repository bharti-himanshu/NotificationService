package com.example.NotificationServiceTry.repositories;

import com.example.NotificationServiceTry.data.entities.SmsRequest;
import org.springframework.data.repository.CrudRepository;


public interface SmsRequestRepository extends CrudRepository<SmsRequest, Integer> {

}
