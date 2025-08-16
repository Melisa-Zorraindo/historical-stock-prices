package com.melisazor.historical_stock_prices.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.melisazor.historical_stock_prices.validation.validators.TickerValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTicker {
    String message() default "Ticker is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
