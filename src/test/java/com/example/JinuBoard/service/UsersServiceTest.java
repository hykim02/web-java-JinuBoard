package com.example.JinuBoard.service;

import com.example.JinuBoard.dto.LoginRequest;
import com.example.JinuBoard.entity.Enum;
import com.example.JinuBoard.entity.Users;
import com.example.JinuBoard.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    private static final Logger log = LoggerFactory.getLogger(UsersServiceTest.class);
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersService usersService;

    @Test
    @DisplayName("회원가입 시 사용자 정보를 DB에 저장한다.")
    public void saveUserTest() {
        // given
        LoginRequest dto = new LoginRequest("jinu@naver.com", "1234", "jinu", Enum.Role.user);

        String encodedPassword = "$2a$10$abcd1234hashed"; // 예시 비밀번호 해시
        when(passwordEncoder.encode("1234")).thenReturn(encodedPassword);

        // when
        usersService.save(dto);

        // then
        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository, times(1)).save(userCaptor.capture());

        Users savedUser = userCaptor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo("jinu@naver.com");
        assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(savedUser.getNickname()).isEqualTo("jinu");
        assertThat(savedUser.getRole()).isEqualTo(Enum.Role.user);
    }
}
