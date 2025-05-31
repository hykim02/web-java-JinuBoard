package com.example.JinuBoard.config;

import com.example.JinuBoard.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Users users;

    public CustomUserDetails(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + users.getRole().name()));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail(); // 로그인 ID로 email 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금되지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 사용 가능
    }
}
