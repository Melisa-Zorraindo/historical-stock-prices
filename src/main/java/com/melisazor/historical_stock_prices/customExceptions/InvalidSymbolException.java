package com.melisazor.historical_stock_prices.customExceptions;

public class InvalidSymbolException extends RuntimeException{
    public InvalidSymbolException(String message) {
        super(message);
    }
}
