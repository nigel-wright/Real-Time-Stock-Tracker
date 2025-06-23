package com.equities.equityplatform.repository;

import com.equities.equityplatform.model.User;
import java.util.List;
import java.util.Map;

public interface UserRepository {
    Integer registerUser(Map<String, String> user_info);
}
