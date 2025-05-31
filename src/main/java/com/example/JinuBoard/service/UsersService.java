package com.example.JinuBoard.service;

import com.example.JinuBoard.dto.LoginRequest;
import com.example.JinuBoard.entity.Users;
import com.example.JinuBoard.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 가입 시, 유저 정보를 저장함
    public void save(LoginRequest dto) {
         usersRepository.save(Users.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .role(dto.getRole())
                .build());
    }

    // 로그인 시, 유저 정보 확인 및 세션 저장
    public boolean checkUser(LoginRequest dto, HttpSession session) {
        Optional<Users> user = usersRepository.findByEmail(dto.getEmail());

        if (user.isPresent()) {
            // 암호화된 비밀번호 비교
            if (bCryptPasswordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
                // 세션에 사용자 정보 저장
                session.setAttribute("loginUser", user.get());
                return true;
            }
        }
        return false;
    }

}
