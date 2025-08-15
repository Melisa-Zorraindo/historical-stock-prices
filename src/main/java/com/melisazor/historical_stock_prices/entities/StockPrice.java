package com.melisazor.historical_stock_prices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice {
    private String symbol;
    private LocalDate lastRefreshed;
    private Map<LocalDate, Double> prices;
}
