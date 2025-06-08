package com.equities.equityplatform.controller;

import com.equities.equityplatform.model.User;
import com.equities.equityplatform.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/insert")
    public String addStock(@RequestBody User user) {
        userRepository.insertUser(user);
        return "User inserted";
    }
}
