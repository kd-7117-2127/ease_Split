package com.ease_splitBackend.ease_splitBackend.repository;

import com.ease_splitBackend.ease_splitBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
