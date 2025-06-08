package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginRepositoryImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    public Boolean userLogin(Login login) {
//        String sql = "SELECT * FROM logins WHERE username = (" +
//                "SELECT user_id FROM users where username = ? or email = ?);";
//        return jdbcTemplate.query(sql, login.getUsername())
//    }
}
