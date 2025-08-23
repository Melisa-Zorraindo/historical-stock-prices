package com.melisazor.historical_stock_prices.services;

import com.melisazor.historical_stock_prices.entities.AlphaVantageResponse;
import com.melisazor.historical_stock_prices.enums.Tickers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlphaVantageService {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${alpha.Vantage.Api.Key}")
    private String alphaVantageApiKey;

    public AlphaVantageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "STOCK_PRICE", key = "#symbol")
    public AlphaVantageResponse getHistoricalStockPrices(Tickers symbol) {
        String partialUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=%s&apikey=%s";
        String apiUrl = String.format(partialUrl, symbol, alphaVantageApiKey);

        return restTemplate
                .exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<AlphaVantageResponse>() {})
                .getBody();
    }
}
