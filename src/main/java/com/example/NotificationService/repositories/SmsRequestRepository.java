package com.example.NotificationService.repositories;

import com.example.NotificationService.entities.SmsRequest;
import org.springframework.data.repository.CrudRepository;


public interface SmsRequestRepository extends CrudRepository<SmsRequest, Integer> {

}
