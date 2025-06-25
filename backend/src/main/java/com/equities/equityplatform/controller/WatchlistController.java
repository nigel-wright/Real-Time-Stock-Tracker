package com.equities.equityplatform.controller;

import com.equities.equityplatform.repository.LoginRepository;
import com.equities.equityplatform.repository.WatchlistEntriesImpl;
import com.equities.equityplatform.repository.WatchlistImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    private final WatchlistImpl watchlist;
    private final WatchlistEntriesImpl watchlistEntries;

    public WatchlistController(WatchlistImpl watchlist, WatchlistEntriesImpl watchlistEntries) {
        this.watchlist = watchlist;
        this.watchlistEntries = watchlistEntries;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createWatchlist(
            @RequestBody Map<String, Object> createReq
    ) {

        int user_id = (int) createReq.get("user_id") == 0 ? 0 : (int) createReq.get("user_id");
        String name = (String) createReq.get("name") == null ? "" : (String) createReq.get("name");

        if (name.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("status", 400, "message", "Title for watchlist needed!")
                    );
        }

        try {
            Integer watchlist_id = watchlist.addToWatchlist(user_id, name);

            if (watchlist_id == 0) {
                return ResponseEntity
                        .internalServerError()
                        .body(Map.of("status", 500, "message", "Could not create watchlist!")
                        );
            }

            return ResponseEntity
                    .ok()
                    .body(Map.of("status", 200, "message", "Watchlist was successfully created!")
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("status", 500, "message", "ERROR: " + e.getMessage())
                    );
        }
    }

    @PostMapping("/{id}/entries")
    public ResponseEntity<Object> addToWatchlist(
            @RequestBody Map<Object, Object> entryReq
    ) {

        int watchlist_id = (int) entryReq.get("watchlist_id") == 0 ? 0 : (int) entryReq.get("watchlist_id");
        String ticker = (String) entryReq.get("ticker") == "" ? "" : (String) entryReq.get("ticker");

        if (watchlist_id == 0 || ticker.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("status", 400, "message", "Please provided a ticker")
                    );
        }

        try {
            Integer entry_id = watchlistEntries.addWatchlistEntry(watchlist_id, ticker);

            if (entry_id == 0) {
                return ResponseEntity
                        .internalServerError()
                        .body(Map.of("status", 500, "message", "Could NOT add entry to watchlist!")
                        );
            }

            return ResponseEntity
                    .ok()
                    .body(Map.of("status", 200, "message", "Ticker was successfully added to watchlist!")
                    );

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("status", 500, "message", "ERROR " + e.getMessage())
                    );
        }
    }

    @GetMapping("/{user_id}/user")
    public Mono<ResponseEntity<Object>> getUserList(
            @PathVariable int user_id
    ) {
        if (user_id <= 0) {
            System.out.println("Please provide a user id!");
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(Map.of("status", 400, "message", ""))
            );
        }

        try {
            List<Map<String, Object>> listData = watchlist.getAllWatchlist(user_id);

            if (listData == null) {
                return Mono.just(ResponseEntity
                        .internalServerError()
                        .body(Map.of("status", 500, "message", "NO list for user provided"))
                );
            }

            return Mono.just(ResponseEntity
                    .ok()
                    .body(Map.of(
                            "status", 200,
                            "message", "Found the user list",
                            "data", listData,
                            "count", listData.size()
                    ))
            );
        } catch (Exception e) {
            System.out.println("ERROR getting the user lists " + e.getMessage());
            return Mono.just(ResponseEntity
                    .internalServerError()
                    .body(Map.of("status", 500, "message", "Ran into an error getting the user lists"))
            );
        }
    }

    @GetMapping("/{watchlist_id}/entries")
    public Mono<ResponseEntity<Object>> getWatchlistEntries(
            @PathVariable int watchlist_id
    ) {
        if (watchlist_id <= 0) {
            System.out.println("Please provide a valid watchlist id");
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(Map.of("status", 400, "message", "Please provide a valid watchlist id!"))
            );
        }

        try {
            List<Map<String, Object>> entriesData = watchlistEntries.getListItems(watchlist_id);

            if (entriesData.isEmpty()) {
                System.out.println("Could not find entries for this watchlist!");
                return Mono.just(ResponseEntity
                        .internalServerError()
                        .body(Map.of("status", 500, "message", "Could not find entries for watchlist id"))
                );
            }

            return Mono.just(ResponseEntity
                    .ok()
                    .body(Map.of(
                            "status", 200,
                            "message", "Got watchlist entries",
                            "data", entriesData,
                            "count", entriesData.size()
                    ))
            );
        } catch (Exception e) {
            System.out.println("ERROR when getting the watchlist entries " + e.getMessage());
            return Mono.just(ResponseEntity
                    .internalServerError()
                    .body(Map.of("status", 500, "message", "ERROR when getting watchlist entries"))
            );
        }
    }
}
