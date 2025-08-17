package com.alamara.journalApp.service;

import com.alamara.journalApp.api.response.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    public RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            log.info("Getting from Redis. Key: {}, Value: {}", key, value);

            if (value == null) {
                log.warn("No value found in Redis for key: {}", key);
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(value.toString(), clazz);
        } catch (Exception e) {
            log.error("Redis get() exception for key {}: {}", key, e.getMessage(), e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
            log.info("Set in Redis. Key: {}, TTL: {}s, Value: {}", key, ttl, jsonValue);
        } catch (Exception e) {
            log.error("Redis set() exception for key {}: {}", key, e.getMessage(), e);
        }
    }
}
