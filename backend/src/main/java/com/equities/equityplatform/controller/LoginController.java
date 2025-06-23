package com.equities.equityplatform.controller;

import com.equities.equityplatform.repository.LoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginRepository loginRepository;

    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> map) {
        String identifier = "";
        String password = !(map.get("password") == null) ? map.get("password") : "";

        if (!(map.get("username") == null)) {
            identifier = map.get("username");
        } else if (!(map.get("email") == null)) {
            identifier = map.get("email");
        }

        String jwtToken = loginRepository.userLogin(identifier, password);

        if (jwtToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", 401, "Failed to sign in!", "Invalid credentials"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "status", 200,
                        "message", "Success, token was generated and the user signed in!",
                        "token",  jwtToken
                )
        );
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> map) {
        
        System.out.println("ID is " + map.get("username"));
        System.out.println("Password is " + map.get("passwordHash"));

        loginRepository.changePassword(map.get("username"), map.get("passwordHash"));
        return "Changing the password was successful!";
    }
}
