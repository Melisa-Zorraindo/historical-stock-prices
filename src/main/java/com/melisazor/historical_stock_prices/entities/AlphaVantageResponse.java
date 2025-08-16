package com.melisazor.historical_stock_prices.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphaVantageResponse {
    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    @JsonProperty("Monthly Adjusted Time Series")
    private Map<String, Map<String, String>> monthlyAdjustedTimeSeries;
}
