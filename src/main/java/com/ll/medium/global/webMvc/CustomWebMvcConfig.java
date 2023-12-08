package com.ll.medium.global.webMvc;

import com.ll.medium.global.app.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfig implements WebMvcConfigurer {

    // 프론트 따로 할 시에 필요
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // cors 작업
        registry.addMapping("/api/**")
                // 도메인 허용, cdpn.io는 코드펜(실제 도메인), 개발 환경에서의 프론트 도메인 localhost:5173
                .allowedOrigins("https://cdpn.io", "http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // 파일 업로드
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/gen/**")
                // /gen/** 도메인으로 들어갔을 때 아래 접근 가능하다.
                .addResourceLocations("file:///" + AppConfig.getGenFileDirPath() + "/");
    }
}
