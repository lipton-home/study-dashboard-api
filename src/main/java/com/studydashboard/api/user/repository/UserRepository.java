package com.studydashboard.api.user.repository;

import com.studydashboard.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    List<User> findUsersByEmailIsIn(List<String> emails);
}
