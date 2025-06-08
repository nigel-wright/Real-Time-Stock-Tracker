package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.User;
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
    public void insertUser(User user) {
        String query = "INSERT INTO users (username, email, full_name) VALUES (?, ?, ?);";
        jdbcTemplate.update(query, user.getUsername(), user.getEmail(), user.getFullName());
    }

    /*
    @Override
    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?;";
        jdbcTemplate.queryForObject(query, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users;";
        jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        User[] user = new User[] {username};
        jdbcTemplate.update(sql, user);
    }
     */
}
