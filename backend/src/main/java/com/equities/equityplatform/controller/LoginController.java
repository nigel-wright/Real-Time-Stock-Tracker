package com.equities.equityplatform.controller;

import com.equities.equityplatform.repository.LoginRepository;
import org.springframework.web.bind.annotation.*;

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
    public String changePassword(@RequestBody String identifier, String password) {
        loginRepository.changePassword(identifier, password);
        return "Changing the password was successful!";
    }
}
