package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.Login;
import com.equities.equityplatform.util.AuthUtil;
import com.equities.equityplatform.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            SELECT l.login_id, l.password
            FROM logins l
            JOIN users u ON u.user_id = l.user_id
            WHERE u.username = ? or u.email = ?
            """;

        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, identifier, identifier);

            if (PasswordUtil.verifyPassword(password, (String) result.get("password"))) {
                String jwt = AuthUtil.createJWT((int) result.get("login_id"), identifier);

                System.out.println("JWT TOKEN IS: " + jwt);
                return jwt;
            } else {
                System.out.println("Could not be verified!");
                return "";
            }
        } catch (EmptyResultDataAccessException ex) {
            System.out.println("User not found: " + identifier);
            return "";
        } catch (Exception ex) {
            System.out.println("Error during login: " + ex.getMessage());
            return "";
        }
    }

    @Override
    public boolean changePassword(String identifier, String password) {
        String hashPassword = PasswordUtil.hashPassword(password);
        String sql = """
                UPDATE logins
                SET password = ?
                WHERE user_id = (SELECT user_id
                                 FROM users
                                 WHERE username = ? or email  = ?);
                """;

        int updatedPassword = jdbcTemplate.update(sql, hashPassword, identifier, identifier);
        return updatedPassword == 1;
    }
}
