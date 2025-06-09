package com.equities.equityplatform.repository;

public interface LoginRepository {
    boolean userLogin(String identifier, String password);
    boolean changePassword(String identifier, String password);
}
