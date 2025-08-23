package com.melisazor.historical_stock_prices.aspects;

import com.melisazor.historical_stock_prices.aspects.RateLimited;
import com.melisazor.historical_stock_prices.exceptions.RateLimitExceededException;
import com.melisazor.historical_stock_prices.services.RedisRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {

    @Autowired
    private RedisRateLimiterService redisRateLimiter;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Around("@annotation(RateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimited rateLimit = method.getAnnotation(RateLimited.class);

        String apiKey = httpServletRequest.getParameter("apiKey");
        String redisShortLimitKey = "short_rate_limit:" + apiKey + ":" + method.getName();
        String redisLongLimitKey = "long_rate_limit:" + apiKey + ":" + method.getName();

        boolean allowed = redisRateLimiter.isAllowed(redisShortLimitKey, rateLimit.perMinuteLimit(),
                rateLimit.minuteWindowLimit(), redisLongLimitKey, rateLimit.perDayLimit(), rateLimit.dayWindowLimit());

        if (!allowed) throw new RateLimitExceededException("Too many requests.");

        return joinPoint.proceed();
    }
}