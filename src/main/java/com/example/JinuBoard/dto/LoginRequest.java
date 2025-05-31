package com.example.JinuBoard.dto;

import com.example.JinuBoard.entity.Enum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // Jackson 역직렬화에 필요
public class LoginRequest {
    @Schema(description = "사용자 이메일", example = "gini@example.com")
    private String email;
    @Schema(description = "비밀번호", example = "1234")
    private String password;
    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;
    @Schema(description = "역할", example = "USER")
    private Enum.Role role;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
