package com.equities.equityplatform.controller;

import com.equities.equityplatform.repository.LoginRepository;
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
    public String signIn(@RequestBody String identifier, String password) {
        loginRepository.userLogin(identifier, password);
        return "Login was successful!";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> map) {
        System.out.println("ID is " + map.get("username"));
        System.out.println("Password is " + map.get("passwordHash"));
        loginRepository.changePassword(map.get("username"), map.get("passwordHash"));
        return "Changing the password was successful!";
    }
}
