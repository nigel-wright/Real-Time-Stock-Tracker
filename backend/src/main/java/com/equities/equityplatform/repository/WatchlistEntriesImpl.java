package com.equities.equityplatform.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WatchlistEntriesImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer addWatchlistEntry(Integer watchlist_id, String ticker) {
        String sql = """
                INSERT INTO watchlist_entries (watchlist_id, ticker)
                SELECT (?), (?)
                WHERE NOT EXISTS (
                  SELECT 1
                  FROM watchlist_entries
                  WHERE watchlist_id = (?)
                  AND ticker = (?)
                )
                RETURNING entry_id;
                """;

        if (watchlist_id <= 0 || ticker == null || ticker.isBlank()) {
            System.out.println("Please provide both a ticker and watchlist_id (or watchlist_id > 0)!");
            throw new IllegalArgumentException("watchlistId must be > 0 and ticker must not be empty");
        }

        try {
            Integer entry_id = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    new Object[]{watchlist_id, ticker, watchlist_id, ticker}
            );

            System.out.println("Entry id is: " + entry_id);

            if (entry_id == null) {
                throw new IllegalStateException("NO entry was made!");
            }

            return entry_id;
        }catch (DataAccessException dae) {
            System.out.println("ERROR when adding to watchlist " + dae.getMessage());
            throw dae;
        }
    }

    public List<Map<String, Object>> getListItems(int watchlist_id) {
        String sql = """
                SELECT ticker, added_at
                FROM watchlist_entries
                WHERE watchlist_id = (?);
                """;

        try {
            List<Map<String, Object>> entries = jdbcTemplate.queryForList(
                    sql,
                    watchlist_id
            );

            if (entries.isEmpty()) {
                throw new IllegalStateException("Could not find entries for this watchlist");
            }

            return entries;
        } catch (DataAccessException dae) {
            System.out.println("ERROR when get items from a watchlist " + dae.getMessage());
            return null;
        }
    }
}
