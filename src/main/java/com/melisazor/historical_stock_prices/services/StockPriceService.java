package com.melisazor.historical_stock_prices.services;

import com.melisazor.historical_stock_prices.entities.AlphaVantageResponse;
import com.melisazor.historical_stock_prices.entities.StockPrice;
import com.melisazor.historical_stock_prices.enums.Tickers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class StockPriceService {

    @Autowired
    private AlphaVantageService alphaVantageService;

    public StockPrice getStockPrices(Tickers symbol, LocalDate startDate, LocalDate endDate) {
        AlphaVantageResponse response = alphaVantageService.getHistoricalStockPrices(symbol);
        StockPrice stockPrice = new StockPrice();
        stockPrice.setSymbol(response.getMetaData().get("2. Symbol"));
        stockPrice.setLastRefreshed(response.getMetaData().get("3. Last Refreshed"));

        Map<String, String> priceMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : response.getMonthlyAdjustedTimeSeries().entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());
            if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                String adjustedClose = entry.getValue().get("5. adjusted close");
                priceMap.put(date.toString(), adjustedClose);
            }
        }

        stockPrice.setPrices(priceMap);
        return stockPrice;
    }
}
