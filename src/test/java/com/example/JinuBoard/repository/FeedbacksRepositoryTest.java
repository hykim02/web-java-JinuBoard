package com.example.JinuBoard.repository;

import com.example.JinuBoard.dto.FeedbackMainDto;
import com.example.JinuBoard.entity.Enum;
import com.example.JinuBoard.entity.Feedbacks;
import com.example.JinuBoard.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class FeedbacksRepositoryTest {

    @Autowired
    private FeedbacksRepository feedbacksRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // 유저 저장
        Users testUser = new Users("test@naver.com", "1234", "지누", Enum.Role.user);
        entityManager.persist(testUser);

        // 피드백 5개 생성 (날짜는 각각 하루씩 차이 나도록)
        for (int i = 1; i <= 5; i++) {
            Feedbacks feedback = Feedbacks.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .category(Enum.Category.etc)
                    .status(Enum.Status.waiting)
                    .userId(testUser)
                    .createdAt(LocalDateTime.now().minusDays(i))
                    .build();
            entityManager.persist(feedback);
        }
    }

    @Test
    @DisplayName("피드백 목록을 내림차순으로 페이징 조회한다")
    void findAllFeedbacksDescTest() {
        // given
        entityManager.flush(); // DB에 반영
        // 페이징 요청: 첫 페이지, 3개씩
        Pageable pageable = PageRequest.of(0, 3);

        // when
        Page<FeedbackMainDto> result = feedbacksRepository.findAllFeedbacksDesc(pageable);

        // then
        assertEquals(3, result.getContent().size());
        assertEquals("제목1", result.getContent().get(0).getTitle()); // 가장 최근 날짜
        assertEquals("제목2", result.getContent().get(1).getTitle());
        assertEquals("제목3", result.getContent().get(2).getTitle());
    }
}
