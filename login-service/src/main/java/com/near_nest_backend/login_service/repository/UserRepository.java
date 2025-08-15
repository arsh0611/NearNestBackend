package com.near_nest_backend.login_service.repository;

import com.near_nest_backend.login_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
