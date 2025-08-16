package com.melisazor.historical_stock_prices.validation.annotations;

import com.melisazor.historical_stock_prices.validation.validators.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "startDate must be earlier than endDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
