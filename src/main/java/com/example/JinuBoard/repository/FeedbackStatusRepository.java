package com.example.JinuBoard.repository;

import com.example.JinuBoard.entity.FeedbackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackStatusRepository extends JpaRepository<FeedbackStatus, Integer> {
}
