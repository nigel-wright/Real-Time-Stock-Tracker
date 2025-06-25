package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class WatchlistImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer addToWatchlist(int user_id, String name) {
        String sql = """
                    INSERT INTO watchlists (user_id, name)
                    VALUES(?, ?)
                    RETURNING watchlist_id;
                    """;
        try {
            Integer watchlist_id = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    new Object[]{user_id, name}
            );

            if (watchlist_id == null) {
                System.out.println("Could not add to watchlist!");
                throw new IllegalStateException("WatchlistId was NOT generated!");
            }

            return watchlist_id;
        } catch (Exception e) {
            System.out.println("ERROR adding to watchlist " + e.getMessage());
            return 0;
        }
    }

    public List<Map<String, Object>> getAllWatchlist(int user_id) {
        String sql = """
                SELECT name, watchlist_id
                FROM watchlists
                WHERE user_id = (?);
                """;

        try {
            List<Map<String, Object>> watchlist_obj = jdbcTemplate.queryForList(
                    sql,
                    user_id
            );

            if (watchlist_obj.isEmpty()) {
                throw new IllegalStateException("NO watchlist for this user was found!");
            }

            return watchlist_obj;
        } catch (DataAccessException dae) {
            System.out.println("ERROR when fetching list of watchlists " + dae.getMessage());
            return null;
        }
    }
}
