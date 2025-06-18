package com.equities.equityplatform.repository;

public interface LoginRepository {
    String userLogin(String identifier, String password);
    boolean changePassword(String identifier, String password);
}
