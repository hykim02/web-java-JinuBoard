package com.example.JinuBoard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FeedbackStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enum.Status fromStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enum.Status toStatus;

    private LocalDateTime changedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedbacks feedbackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    private Users changedBy;

    @Builder
    public FeedbackStatus(Feedbacks feedbackId, Users changedBy, Enum.Status fromStatus, Enum.Status toStatus) {
        this.feedbackId = feedbackId;
        this.changedBy = changedBy;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.changedAt = LocalDateTime.now();
    }
}
