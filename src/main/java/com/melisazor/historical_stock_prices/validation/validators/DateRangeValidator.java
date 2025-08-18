package com.melisazor.historical_stock_prices.validation.validators;

import com.melisazor.historical_stock_prices.validation.annotations.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import java.time.LocalDate;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object[]> {

    @Override
    public boolean isValid(Object[] params, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate start = (LocalDate) params[1];
        LocalDate end = (LocalDate) params[2];

        boolean valid = true;
        if (start.isAfter(today)) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Start date must be in the past").addConstraintViolation();
        }

        if (end.isAfter(today)) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be in the past or in the present.")
                    .addConstraintViolation();
        }

        if (end.isBefore(start)) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be after start date.")
                    .addConstraintViolation();
        }

        return valid;
    }
}
