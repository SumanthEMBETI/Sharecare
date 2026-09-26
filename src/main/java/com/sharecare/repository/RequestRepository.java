package com.sharecare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharecare.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByOrderByIdDesc();

}