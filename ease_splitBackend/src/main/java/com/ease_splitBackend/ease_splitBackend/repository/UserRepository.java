package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
