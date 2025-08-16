package com.melisazor.historical_stock_prices.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockPriceRequest {
    private String symbol;
    private LocalDate startDate;
    private LocalDate endDate;
}
