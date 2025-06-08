package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.User;
import java.util.List;

public interface UserRepository {
    void insertUser(User user);
    /*
    User findByUsername(String username);
    List<User> findAll();
    void updateUser(User user);
    void deleteUser(String username);
    */
}
