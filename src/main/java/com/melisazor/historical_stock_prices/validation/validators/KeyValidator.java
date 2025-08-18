package com.melisazor.historical_stock_prices.validation.validators;

import com.melisazor.historical_stock_prices.validation.annotations.ValidKey;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class KeyValidator implements ConstraintValidator<ValidKey, String> {

    @Value("${user.Api.Key}")
    private String userApiKey;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isBlank() && value.equals(userApiKey);
    }
}
