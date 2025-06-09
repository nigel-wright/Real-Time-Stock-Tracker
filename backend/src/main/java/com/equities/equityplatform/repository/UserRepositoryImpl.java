package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.User;
import com.equities.equityplatform.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void registerUser(User user) {
        String userSql = "INSERT INTO users (username, email, full_name) VALUES (?, ?, ?) RETURNING user_id;";
        Integer userId = jdbcTemplate.queryForObject(userSql, Integer.class, user.getUsername(), user.getEmail(), user.getFullName());

        String hashedPassword = PasswordUtil.hashPassword(user.getLogin().getPasswordHash());

        String loginSql = "INSERT INTO logins (user_id, password) VALUES (?, ?);";
        jdbcTemplate.update(loginSql, userId, hashedPassword);
    }
}
