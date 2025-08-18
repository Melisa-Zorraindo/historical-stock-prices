package com.melisazor.historical_stock_prices.validation.annotations;

import com.melisazor.historical_stock_prices.validation.validators.KeyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = KeyValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidKey {
    String message() default "Api Key is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

