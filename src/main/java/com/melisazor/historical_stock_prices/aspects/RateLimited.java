package com.melisazor.historical_stock_prices.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimited {

    int perMinuteLimit();
    int minuteWindowLimit();
    int perDayLimit();
    int dayWindowLimit();
}
