package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.data.dto.request.blackList.BlacklistNumbersReq;
import com.example.NotificationServiceTry.data.dto.response.blackList.BlacklistSaveRes;
import com.example.NotificationServiceTry.data.entities.BlackList;
import com.example.NotificationServiceTry.repositories.BlackListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlacklistService {
    @Autowired
    private BlackListRepository blackListRepository;
    private static final Logger log = LoggerFactory.getLogger(SmsCrudService.class);

    @Autowired
    Jedis jedis;

    public BlacklistSaveRes saveBlackList(BlacklistNumbersReq blacklistNumbersReq){
        try {
            jedis.sadd("blackList", blacklistNumbersReq.getPhoneNumbers().stream().toArray(String[]::new));
            blackListRepository.saveAll(blacklistNumbersReq.getPhoneNumbers().stream().map(BlackList::new).collect(Collectors.toSet()));
            return BlacklistSaveRes.builder()
                    .data("Successfully Blacklisted")
                    .build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return BlacklistSaveRes.builder()
                    .data("Error saving blackList")
                    .build();
        }

    }

    public BlacklistNumbersReq getBlackList() {
        Set<String> blackList = jedis.smembers("blackList");
        BlacklistNumbersReq blacklistNumbersReq = BlacklistNumbersReq.builder()
                .phoneNumbers(blackList)
                .build();
        return blacklistNumbersReq;
    }

    public void deleteNumber(BlacklistNumbersReq blacklistNumbersReq) throws NoSuchElementException {
            jedis.srem("blackList", blacklistNumbersReq.getPhoneNumbers().stream().toArray(String[]::new));
            blackListRepository.deleteAll(blacklistNumbersReq.getPhoneNumbers().stream().map(BlackList::new).collect(Collectors.toSet()));
    }

}
