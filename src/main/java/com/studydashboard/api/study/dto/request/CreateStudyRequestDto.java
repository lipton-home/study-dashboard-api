package com.studydashboard.api.study.dto.request;

import com.studydashboard.api.study.type.StudyStatus;
import com.studydashboard.api.study.type.StudyType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateStudyRequestDto {
    private String title;
    private String description;
    private StudyType type;
    private StudyStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
