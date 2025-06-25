package com.equities.equityplatform.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Watchlist {

    private int watchlist_id;
    private int user_id;
    private Stock ticker;
    private Stock stock;
    private LocalDateTime added_at;


    public Watchlist(int watchlist_id,
                          int user_id,
                          Stock stock,
                          LocalDateTime added_at) {
        this.watchlist_id = watchlist_id;
        this.user_id  = user_id;
        this.stock   = stock;
        this.added_at = added_at;
    }

    // Getters
    public int getWatchlistID() {
        return this.watchlist_id;
    }

    public int getUserID() {
        return this.user_id;
    }

    public Stock getStock() {
        return this.stock;
    }

    public LocalDateTime getAddedDate() {
        return this.added_at;
    }
}
