package com.example.NotificationServiceTry.services;

import com.example.NotificationServiceTry.dto.BlacklistNumbersDto;
import com.example.NotificationServiceTry.entities.BlackList;
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

    public void saveBlackList(BlacklistNumbersDto blacklistNumbersDto){
        try {
            jedis.sadd("blackList", blacklistNumbersDto.getPhone_numbers().stream().toArray(String[]::new));
            blackListRepository.saveAll(blacklistNumbersDto.getPhone_numbers().stream().map(BlackList::new).collect(Collectors.toSet()));
        }
        catch (Exception e){
            log.error(e.getMessage());
        }

    }

    public BlacklistNumbersDto getBlackList() {
        Set<String> blackList = jedis.smembers("blackList");
        BlacklistNumbersDto blacklistNumbersDto = new BlacklistNumbersDto(blackList);
        return blacklistNumbersDto;
    }

    public void deleteNumber(BlacklistNumbersDto blacklistNumbersDto) throws NoSuchElementException {
            jedis.srem("blackList", blacklistNumbersDto.getPhone_numbers().stream().toArray(String[]::new));
            blackListRepository.deleteAll(blacklistNumbersDto.getPhone_numbers().stream().map(BlackList::new).collect(Collectors.toSet()));
    }

}
