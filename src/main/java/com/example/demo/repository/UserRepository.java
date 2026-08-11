package com.example.demo.repository;

import java.util.Optional;

import org.springframework.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

}
