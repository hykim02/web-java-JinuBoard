package com.example.JinuBoard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "feedback_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedbacks feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Users adminId;

    @Builder
    public FeedbackComments(Feedbacks feedbackId, Users adminId, String content) {
        this.feedbackId = feedbackId;
        this.adminId = adminId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
