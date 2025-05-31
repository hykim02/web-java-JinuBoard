package com.example.JinuBoard.service;

import com.example.JinuBoard.config.CustomUserDetails;
import com.example.JinuBoard.entity.Users;
import com.example.JinuBoard.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// 스프링 시큐리티에서 사용자 정보를 가져옴
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    // 사용자 이메일로 사용자 정보를 가져오는 메소드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다: " + email));
        return new CustomUserDetails(user);
    }
}
