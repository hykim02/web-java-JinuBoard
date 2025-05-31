package com.example.JinuBoard.repository;

import com.example.JinuBoard.entity.FeedbackComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackCommentsRepository extends JpaRepository<FeedbackComments, Integer> {
}
