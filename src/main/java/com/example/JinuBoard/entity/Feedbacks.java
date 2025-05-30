package com.example.JinuBoard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "feedbacks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedbacks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enum.Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Enum.Status status;

    private Boolean isAnonymous;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 작성자
    @Column(nullable = false)
    private Users userId;

    @Builder
    public Feedbacks(String title, String content, Enum.Category category, Enum.Status status, Boolean isAnonymous, Users userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.status = status;
        this.isAnonymous = isAnonymous;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
