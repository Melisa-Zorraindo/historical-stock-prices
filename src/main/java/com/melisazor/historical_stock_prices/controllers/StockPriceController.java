package com.melisazor.historical_stock_prices.controllers;

import com.melisazor.historical_stock_prices.customExceptions.InvalidSymbolException;
import com.melisazor.historical_stock_prices.entities.StockPrice;
import com.melisazor.historical_stock_prices.enums.Tickers;
import com.melisazor.historical_stock_prices.services.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

@RestController
@Validated
public class StockPriceController {

    @Autowired
    StockPriceService stockPriceService;

    @GetMapping("/api/historical/{symbol}")
    public ResponseEntity<StockPrice> getStockPrices(@PathVariable("symbol") String symbol) throws IOException, InvalidSymbolException, ParseException {
        Tickers ticker;
        try {
            ticker = Tickers.valueOf(symbol.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSymbolException("Ticker " + symbol + " is not valid. Valid tickers are: " + Arrays.toString(Tickers.values()));
        }

        StockPrice stockPrice = stockPriceService.getStockPrices(ticker);
        return new ResponseEntity<>(stockPrice, HttpStatus.OK);
    }
}
