package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Login;
import com.equities.equityplatform.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepositoryImpl implements LoginRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean userLogin(String identifier, String password) {
        String sql = """
            SELECT l.password
            FROM logins l
            JOIN users u ON l.used_id = u.user_id
            WHERE u.username = ? or u.email = ?
            """;
        try {
            String storedHash = jdbcTemplate.queryForObject(sql, String.class, identifier, identifier);
            return PasswordUtil.verifyPassword(storedHash, password);
        } catch (Exception ex) {
            System.out.println("There was an issue login the user in.");
            return false;
        }
    }

    @Override
    public boolean changePassword(String identifier, String password) {
        String hashPassword = PasswordUtil.hashPassword(password);
        String sql = """
                UPDATE logins
                SET password = ?
                WHERE user_id = (SELECT user
                                 FROM users
                                 WHERE username = ? or email  = ?);
                """;

        int updatedPassword = jdbcTemplate.update(sql, hashPassword, identifier, identifier);
        return updatedPassword == 1;
    }
}
