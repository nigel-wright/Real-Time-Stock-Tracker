package com.equities.equityplatform.service;

import jep.SubInterpreter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PolygonService {

    @Value("${polygon.api.key}")
    private String apiKey;

    @Value("${polygon.base.url}")
    private String baseUrl;

    private final WebClient webClient;

    // Constructor with WebClient.Builder injection
    public PolygonService(WebClient.Builder builder) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            System.out.println("WARNING: Base URL is null or empty, using default");
            baseUrl = "https://api.polygon.io";
        }

        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("User-Agent", "EquityPlatform/1.0")
                .build();

        System.out.println("WebClient created successfully with base URL: " + baseUrl);
    }

    public Mono<String> getStockInfo(String ticker) {
        System.out.println("=== PolygonService.getStockInfo called ===");
        System.out.println("Ticker: " + ticker);
        System.out.println("Base URL: " + baseUrl);

        String uri = String.format("/v3/reference/tickers/%s?apikey=%s", ticker, apiKey);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> {
                    System.out.println("SUCCESS: Fetched Info!");
                    System.out.println("Response length: " + (response != null ? response.length() : 0));
                })
                .doOnError(error -> {
                    System.err.println("Error: " + error.getMessage());
                    System.err.println("Attempted URL: " + baseUrl + uri);
                });
    }

    // Final to make the fetch to polygon for the data
    public String getStockData(String ticker,
                             String start_date,
                             String end_date,
                             String interval) {
        try {
            System.out.println("=== PolygonService.getStockData called ===");

            // Check for "End Date"
            String endDate = (end_date == null) ? LocalDate.now().toString() : end_date;

            // Built the URI
            String uri = String.format("/v2/aggs/ticker/%s/range/1/%s/%s/%s?adjusted=true&sort=asc&apiKey=%s",
                                                        ticker, interval, start_date, endDate, apiKey);

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> {
                        System.out.println("SUCCESS: Fetched Data!");
                        System.out.println("Response length: " + (response != null ? response.length() : 0));
                    })
                    .doOnError(error -> {
                        System.err.println("Error: " + error.getMessage());
                        System.err.println("Attempted URL: " + baseUrl + uri);
                    })
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during stock data fetch!", e);
        }
    }

    public String getRelatedStock(String ticker) {
        try {
            System.out.println("=== PolygonService.getRelatedStock called ===");

            String uri = String.format("/v1/related-companies/%s?apiKey=%s", ticker, apiKey);

            System.out.println("URI is: " + uri);

            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(response -> {
                        System.out.println("SUCCESS: Fetched Related Stock Data!");
                        System.out.println("Response length: " + (response != null ? response.length() : 0));
                    })
                    .doOnError(error -> {
                        System.err.println("Error: " + error.getMessage());
                        System.err.println("Attempted URL: " + baseUrl + uri);
                    })
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during related stock fetch!");
        }
    }
}