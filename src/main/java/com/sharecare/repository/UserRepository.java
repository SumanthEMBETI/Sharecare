package com.sharecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharecare.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhone(String phone);

    User findByPhone(String phone);
}