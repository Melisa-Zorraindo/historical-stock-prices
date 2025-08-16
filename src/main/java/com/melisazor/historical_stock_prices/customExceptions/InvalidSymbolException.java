package com.melisazor.historical_stock_prices.customExceptions;

public class InvalidSymbolException extends IllegalArgumentException{
    public InvalidSymbolException(String message) {
        super(message);
    }
}
