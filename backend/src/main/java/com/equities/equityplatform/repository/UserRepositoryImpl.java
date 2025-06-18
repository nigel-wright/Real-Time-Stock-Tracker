package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Login;
import com.equities.equityplatform.model.User;
import com.equities.equityplatform.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int registerUser(Map<String, String> map) {
        try {
            if (map.get("username") && map.get("email") == null || map.get("full_name") == null || map.get("password")) {
                throw new IllegalArgumentException("Missing required register fields!");
            }

            String userSql = "INSERT INTO users (username, email, full_name) VALUES (?, ?, ?) RETURNING user_id;";

            int userId = jdbcTemplate.queryForObject(
                    userSql,
                    Integer.class,
                    new Object[]{map.get("username"), map.get("email"), map.get("full_name")
            );

            if (userId == null) {
                throw new IllegalStateException("UserId was NOT generated!");
            }

            // Hash raw password once
            String hashedPassword = PasswordUtil.hashPassword(map.get("password"));
            String loginSql = "INSERT INTO logins (user_id, password) VALUES (?, ?);";
            jdbcTemplate.update(loginSql, userId, hashedPassword);
            return userId;

        } catch (Exception ex) {
            System.out.println("There was an issue registering the user: " + ex.message());
            return 0;
        }
    }
}
