package com.melisazor.historical_stock_prices.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice {
    private String symbol;
    private String lastRefreshed;
    private Map<String, String> prices;
}
