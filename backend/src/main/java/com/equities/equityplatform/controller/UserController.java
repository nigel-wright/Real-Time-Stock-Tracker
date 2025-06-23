package com.equities.equityplatform.controller;

import com.equities.equityplatform.model.User;
import com.equities.equityplatform.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> map) {

        if (userRepository.registerUser(map) == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", 401, "message", "Invalid credentials"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "status", 200,
                        "message", "Success!"
                )
        );
    }
}
