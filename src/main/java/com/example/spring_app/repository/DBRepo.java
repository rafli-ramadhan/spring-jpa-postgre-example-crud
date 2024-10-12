package com.example.spring_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_app.model.Data;

@Repository
public interface DBRepo extends JpaRepository<Data, Long> {
    Optional<Data> findByContentId(String contentId);
    int deleteByContentId(String contentId);
}
