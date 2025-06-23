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
    public Integer registerUser(Map<String, String> map) {
        try {
            String userSql = """
                INSERT INTO users (username, email, full_name)
                VALUES (?, ?, ?)
                RETURNING user_id;
               """;

            String username = map.get("username");
            String email = map.get("email");
            String fullName = map.get("full_name");

            if (username.isEmpty() || email.isEmpty() || fullName.isEmpty()) {
                System.out.println("The user could not be registered!");
                return 0;
            }

            Integer userId = jdbcTemplate.queryForObject(
                    userSql,
                    Integer.class,
                    new Object[]{username, email, fullName}
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
            System.out.println("There was an issue registering the user: " + ex.getMessage());
            return 0;
        }
    }
}
