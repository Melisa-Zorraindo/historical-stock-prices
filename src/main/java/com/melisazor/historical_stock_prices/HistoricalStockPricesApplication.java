package com.melisazor.historical_stock_prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HistoricalStockPricesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistoricalStockPricesApplication.class, args);
	}

}
