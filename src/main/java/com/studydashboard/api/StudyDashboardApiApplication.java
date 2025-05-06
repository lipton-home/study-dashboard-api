package com.studydashboard.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudyDashboardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyDashboardApiApplication.class, args);
    }

}
