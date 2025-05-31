package com.example.JinuBoard.repository;

import com.example.JinuBoard.dto.FeedbackMainDto;
import com.example.JinuBoard.entity.Feedbacks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, Integer> {

    // 피드백 목록 조회
    @Query("SELECT new com.example.JinuBoard.dto.FeedbackMainDto(f.title, f.category, f.status, f.createdAt, f.userId) " +
            "FROM Feedbacks f ORDER BY f.createdAt DESC")
    Page<FeedbackMainDto> findAllFeedbacksDesc(Pageable pageable);
}
