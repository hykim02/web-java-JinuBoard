package com.example.JinuBoard.repository;

import com.example.JinuBoard.entity.Enum;
import com.example.JinuBoard.entity.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTest {

    private final UsersRepository usersRepository;
    private static Users users;

    @Autowired
    public UsersRepositoryTest(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // 각각의 테스트가 실행되기 전 실행됨
    @BeforeEach
    public void setUp() {
        users = Users.builder()
                .email("jinu@naver.com")
                .password("1234")
                .nickname("jinu")
                .role(Enum.Role.user)
                .build();
    }

    @AfterEach
    public void delete() {
        usersRepository.deleteAll();
    }

    @Test
    @DisplayName("email을 통해 사용자를 조회한다.")
    public void findByEmailTest() {
        // given
        usersRepository.save(users);
        String email = "jinu@naver.com";

        // when
        Optional<Users> user = usersRepository.findByEmail(email);

        // then
        assertThat(user).isPresent();
        assertThat(user.get().getEmail()).isEqualTo(email);
    }
}
