package com.example.JinuBoard.controller;

import com.example.JinuBoard.dto.LoginRequest;
import com.example.JinuBoard.entity.Enum;
import com.example.JinuBoard.entity.Users;
import com.example.JinuBoard.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springdoc.core.converters.ResponseSupportConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UsersService usersService() {
            return Mockito.mock(UsersService.class);
        }

        @Bean
        public ResponseSupportConverter responseSupportConverter() {
            return Mockito.mock(ResponseSupportConverter.class);
        }
    }

    @Autowired
    private UsersService usersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // 인증된 사용자 역할 부여
    @DisplayName("회원가입 성공")
    void registerTest() throws Exception {
        // given
        LoginRequest request = new LoginRequest("jinu@naver.com", "1234", "jinu", Enum.Role.user);

        // doNothing()은 void 메서드가 호출되더라도 예외가 발생하지 않도록 함
        Mockito.doNothing().when(usersService).save(any(LoginRequest.class));

        // when & then
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("회원가입이 완료되었습니다."));
    }

    @Test
    @WithMockUser
    @DisplayName("로그인 성공 시 200 반환")
    void loginSuccessTest() throws Exception {
        LoginRequest request = new LoginRequest("test@naver.com", "1234");

        // 세션 테스트 하기 위한 mock session
        MockHttpSession session = new MockHttpSession();

        // usersService.checkUser 호출 시 true 반환하도록 설정
        Mockito.when(usersService.checkUser(any(LoginRequest.class), any(HttpSession.class))).thenReturn(true);

        mockMvc.perform(post("/login")
                        .session(session)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("로그인 성공하였습니다."));
    }

    @Test
    @DisplayName("로그인 실패 시 401 반환")
    void loginFailTest() throws Exception {
        LoginRequest request = new LoginRequest("wrong@naver.com", "wrongpass", "jinu", Enum.Role.user);

        Mockito.when(usersService.checkUser(any(LoginRequest.class), any(HttpSession.class))).thenReturn(false);

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    @Test
    @WithMockUser
    @DisplayName("로그아웃 시 세션 무효화 및 204 반환")
    void logoutTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Users testUser = Users.builder()
                .email("test@naver.com")
                .password("1234")
                .nickname("지누")
                .role(Enum.Role.user)
                .build();

        session.setAttribute("loginUser", testUser);

        mockMvc.perform(post("/logout").session(session)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("로그인된 사용자가 /main 접속 시 환영 메시지 반환")
    void mainPageWithLogin() throws Exception {
        Users user = Users.builder()
                .email("test@naver.com")
                .nickname("지누")
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUser", user);

        mockMvc.perform(get("/main").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string("지누님, 환영합니다!"));
    }

    @Test
    @WithMockUser
    @DisplayName("비로그인 사용자가 /main 접속 시 401 반환")
    void mainPageWithoutLogin() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("로그인이 필요합니다."));
    }
}
