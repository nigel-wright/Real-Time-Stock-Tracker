package com.equities.equityplatform.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyService {

    @Value("${app.secret-key}")
    private String secretKey;

    public String getSecretKey() {
        return this.secretKey;
    }

    public void printSecretKey() {
        System.out.println("Hi, you have a secret key, it is " + this.secretKey);
    }
}
