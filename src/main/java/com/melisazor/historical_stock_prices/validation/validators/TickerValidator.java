package com.melisazor.historical_stock_prices.validation.validators;

import com.melisazor.historical_stock_prices.enums.Tickers;
import com.melisazor.historical_stock_prices.validation.annotations.ValidTicker;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TickerValidator implements ConstraintValidator<ValidTicker, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;

        try {
            Tickers.valueOf(value.toUpperCase());
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
