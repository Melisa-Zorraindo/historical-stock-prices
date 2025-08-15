package com.melisazor.historical_stock_prices.controllers;

import com.melisazor.historical_stock_prices.entities.StockPrice;
import com.melisazor.historical_stock_prices.services.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockPriceController {

    @Autowired
    StockPriceService stockPriceService;

    @GetMapping("/api/historical/{symbol}")
    public ResponseEntity<StockPrice> getStockPrices(@PathVariable String symbol) {
        StockPrice stockPrice = stockPriceService.getStockPrices(symbol);

        return new ResponseEntity<>(stockPrice, HttpStatus.OK);
    }
}
