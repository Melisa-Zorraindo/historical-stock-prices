package com.melisazor.historical_stock_prices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisRateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isAllowed(String shortKeyLimit, int perMinuteLimit, int minuteWindowLimit, String redisLongLimitKey,
                             int perDayLimit, int dayWindowLimit) {

        Long shortLimitCount = redisTemplate.opsForValue().increment(shortKeyLimit);
        if (shortLimitCount != null && shortLimitCount == 1) {
            redisTemplate.expire(shortKeyLimit, Duration.ofMinutes(minuteWindowLimit));
        }

        Long longLimitCount = redisTemplate.opsForValue().increment(redisLongLimitKey);
        if (longLimitCount != null && longLimitCount == 1) {
            redisTemplate.expire(redisLongLimitKey, Duration.ofDays(dayWindowLimit));
        }

        return (shortLimitCount != null && shortLimitCount <= perMinuteLimit)
                && (longLimitCount != null && longLimitCount <= perDayLimit);
    }
}
