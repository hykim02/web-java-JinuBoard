package com.example.JinuBoard.dto;


import com.example.JinuBoard.entity.Enum;
import com.example.JinuBoard.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackMainDto {
    private String title;
    private Enum.Category category;
    private Enum.Status status;
    private LocalDateTime createdAt;
    private Users userId;
}
