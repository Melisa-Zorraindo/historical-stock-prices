package com.melisazor.historical_stock_prices.controllers;

import com.melisazor.historical_stock_prices.entities.StockPrice;
import com.melisazor.historical_stock_prices.enums.Tickers;
import com.melisazor.historical_stock_prices.services.StockPriceService;
import com.melisazor.historical_stock_prices.validation.annotations.ValidDateRange;
import com.melisazor.historical_stock_prices.validation.annotations.ValidKey;
import com.melisazor.historical_stock_prices.validation.annotations.ValidTicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@Validated
public class StockPriceController {

    @Autowired
    StockPriceService stockPriceService;

    @GetMapping("/api/historical/{symbol}/{startDate}/{endDate}")
    @ValidDateRange
    public ResponseEntity<StockPrice> getStockPrices(
            @PathVariable @ValidTicker String symbol,
            @PathVariable LocalDate startDate,
            @PathVariable LocalDate endDate,
            @RequestParam("apiKey") @ValidKey String apiKey) throws IOException {

        Tickers ticker = Tickers.valueOf(symbol.toUpperCase());

        StockPrice stockPrice = stockPriceService.getStockPrices(ticker, startDate, endDate);
        return new ResponseEntity<>(stockPrice, HttpStatus.OK);
    }
}
