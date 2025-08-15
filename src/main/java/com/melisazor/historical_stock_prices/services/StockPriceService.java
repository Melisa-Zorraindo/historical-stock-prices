package com.melisazor.historical_stock_prices.services;

import com.melisazor.historical_stock_prices.customExceptions.InvalidSymbolException;
import com.melisazor.historical_stock_prices.entities.AlphaVantageResponse;
import com.melisazor.historical_stock_prices.entities.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockPriceService {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${alpha.Vantage.Api.Key}")
    private String alphaVantageApiKey;

    public StockPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public StockPrice getValidSymbols(String symbol) throws IOException {
        String partialUrl = "https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=%s";
        String apiUrl = String.format(partialUrl, alphaVantageApiKey);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);

        String csvData = response.getBody();
        if(csvData == null || csvData.isBlank()) {
            throw new RuntimeException("No data received from Alpha Vantage");
        }

        // parse tickers
        List<String> symbols = new ArrayList<>();
        String[] lines = csvData.split("\n");

        for (int i = 1; i < lines.length; i++) {
            String[] columns = lines[i].split(",");

            String ticker = columns[0].trim();
            symbols.add(ticker);
        }

        // return early if ticker is not valid
        if (!symbols.contains(symbol)) {
            throw new InvalidSymbolException("Symbol " + symbol + " is not valid");
        }

        return getStockPrices(symbol);
    }

    public StockPrice getStockPrices(String symbol){
        String partialUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=%s&apikey=%s";
        String apiUrl = String.format(partialUrl, symbol, alphaVantageApiKey);

        ResponseEntity<AlphaVantageResponse> exchange = restTemplate
                .exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        assert exchange.getBody() != null;
        return mapToStockPrice(exchange.getBody());
    }

    private StockPrice mapToStockPrice(AlphaVantageResponse response) {
        StockPrice stockPrice = new StockPrice();
        stockPrice.setSymbol(response.getMetaData().get("2. Symbol"));
        stockPrice.setLastRefreshed(LocalDate.parse(response.getMetaData().get("3. Last Refreshed")));

        Map<LocalDate, Double> priceMap = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : response.getMonthlyAdjustedTimeSeries().entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());
            Double adjustedClose = Double.parseDouble(entry.getValue().get("5. adjusted close"));
            priceMap.put(date, adjustedClose);

        }

        stockPrice.setPrices(priceMap);
        return stockPrice;
    }
}
