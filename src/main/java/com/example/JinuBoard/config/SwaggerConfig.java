package com.example.JinuBoard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("JinuBoard API Document")
                .description("카카오 기반 챗봇 서비스 사용자 피드백을 관리하는 웹 서비스의 OpenAPI 문서입니다.")
                .version("1.0");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
