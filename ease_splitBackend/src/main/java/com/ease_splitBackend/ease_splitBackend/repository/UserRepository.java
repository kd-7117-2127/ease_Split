package com.easesplit.ease_splitBackend.repository;

import com.easesplit.ease_splitBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
