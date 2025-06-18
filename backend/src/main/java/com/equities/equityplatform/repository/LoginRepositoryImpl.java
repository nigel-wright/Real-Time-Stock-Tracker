package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Login;
import com.equities.equityplatform.service.MyService;
import com.equities.equityplatform.util.AuthUtil;
import com.equities.equityplatform.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.equities.equityplatform.util.AuthUtil.createJWT;

@Repository
public class LoginRepositoryImpl implements LoginRepository{


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String userLogin(String identifier, String password) {
        String sql = """
            SELECT l.password, l.login_id
            FROM logins l
            JOIN users u ON l.used_id = u.user_id
            WHERE u.username = ? or u.email = ?
            """;
        try {
            // Fetch login data from DB
            Login login = jdbcTemplate.queryForObject(sql, Object.class, new Object[]{identifier, identifier});

            if (login != null && PasswordUtil.verifyPassword(login.getPasswordHash(), password)) {
                return AuthUtil.createJWT(login.getLoginId(), identifier);
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("There was an issue login the user in.");
            return null;
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
