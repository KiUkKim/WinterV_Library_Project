package com.eunyeong.book.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Application: 앞으로 만들 프로젝트의 메인 클래스
@EnableJpaAuditing //JPA Auditing 활성화
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // 스프링 시큐리티 기능 일시 제거
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args); // 내장 WAS 실행
    }
}