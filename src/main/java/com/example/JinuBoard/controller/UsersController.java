package com.example.JinuBoard.controller;

import com.example.JinuBoard.dto.LoginRequest;
import com.example.JinuBoard.entity.Users;
import com.example.JinuBoard.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.ResponseSupportConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users API", description = "사용자 회원가입 및 로그인/로그아웃 기능")
public class UsersController {

    private final UsersService usersService;
    private final ResponseSupportConverter responseSupportConverter;

    // 회원 가입
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임, 권한을 받아 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success: 회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request: 회원가입 실패")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        usersService.save(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력받아 로그인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success: 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request: 로그인 실패"),
            @ApiResponse(responseCode = "401", description = "UnAuthorized: 사용자 정보가 존재하지 않습니다.")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
        boolean chk = usersService.checkUser(request, session);

        if (chk) {
            return ResponseEntity.ok("로그인 성공하였습니다.");
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    // 메인 화면 접속 시, 로그인 상태 유지 확인
    @Operation(summary = "로그인 세션 유지", description = "로그인 정보를 세션에 저장하여 상태를 유지합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success: 세션 유지 성공"),
            @ApiResponse(responseCode = "400", description = "Bad Request: 세션 유지 실패"),
            @ApiResponse(responseCode = "401", description = "UnAuthorized: 로그인이 필요합니다.")
    })
    @GetMapping("/main")
    public ResponseEntity<String> mainPage(HttpSession session) {
        Users loginUser = (Users) session.getAttribute("loginUser");

        if (loginUser != null) {
            return ResponseEntity.ok(loginUser.getNickname() + "님, 환영합니다!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
    }

    // 로그 아웃
    @Operation(summary = "로그아웃", description = "세션 기반 로그아웃 처리")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 제거
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
