package com.melisazor.historical_stock_prices.controllers;

import com.melisazor.historical_stock_prices.entities.StockPrice;
import com.melisazor.historical_stock_prices.services.StockPriceService;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class StockPriceController {

    @Autowired
    StockPriceService stockPriceService;

    @GetMapping("/api/historical/{symbol}")
    public ResponseEntity<StockPrice> getStockPrices(@PathVariable("symbol") @Size(min = 1, max = 5, message = "Ticker must be between 1 and 5 characters") String symbol) throws IOException {
        StockPrice stockPrice = stockPriceService.getValidSymbols(symbol);
        return new ResponseEntity<>(stockPrice, HttpStatus.OK);
    }
}
