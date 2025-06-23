package com.equities.equityplatform.controller;

import com.equities.equityplatform.service.PolygonService;
import com.equities.equityplatform.service.PythonService;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
public class StockController {

    // Declare references to services
    private final PolygonService polygonService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StockController(PolygonService polygonService) {
        this.polygonService = polygonService;
    }

    @GetMapping("/ticker/info/{ticker}")
    public Mono<String> getStockInfo(@PathVariable String ticker) {
        if (ticker == null || ticker.trim().isEmpty()) {
            return Mono.just("Error: Ticker cannot be empty");
        }
        return polygonService.getStockInfo(ticker);
    }

    @GetMapping("/ticker/data/{ticker}")
    public ResponseEntity<Map<String, Object>> getStockData(
            @PathVariable String ticker,
            @RequestParam("start") String startDate,
            @RequestParam(value = "end", required = false) String endDate,
            @RequestParam(value = "interval", defaultValue = "day") String interval
        ) {

        // Validate ticker, negate startDate (hardcoded)
        if (ticker == null || ticker.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("status", 400, "message", "Ticker cannot be empty")
                    );
        }

        // Fetch and check the data from the endpoint
        String data = polygonService.getStockData(ticker, startDate, endDate, interval);
        if (data.isEmpty() || data.isBlank()) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("status",500,"message","Failed to fetch stock data")
                    );
        }

        // Declare the list and transform data
        List<Map<String, Object>> dataList;
        try {
            dataList = objectMapper.convertValue(
                    // readTree gives you a JsonNode
                    objectMapper.readTree(data).get("results"),
                    new TypeReference<>() {}
            );

        } catch (Exception e) {
            System.out.println("ERROR: Ran into error " + e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("status", 500, "message", "Failed to convert data to list")
                    );
        }

        if (dataList.isEmpty()) {
            return ResponseEntity
                    .status(204)
                    .body(Map.of("status", 204, "message", "NO data availbale")
                    );
        }

        return ResponseEntity.ok(
                Map.of(
                    "status", 200, 
                    "message", "Success!",
                    "data", dataList,
                        "count", dataList.size()
                )
            );
    }

//    @GetMapping("/stocks/related/{Ticker}")
//    public Mono<ResponseEntity<Object>> getRelatedStock(@RequestParam String ticker) {
//        if (ticker.isEmpty()) {
//            return Mono.just(ResponseEntity
//                            .badRequest()
//                            .body(Map.of("status", 400, "message", "Cannot be null ticker input!")
//                            ));
//        }
//
//
//        return Mono.just();
//    }
}