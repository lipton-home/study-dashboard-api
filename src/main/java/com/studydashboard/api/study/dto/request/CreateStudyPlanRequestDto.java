package com.studydashboard.api.study.dto.request;

import com.studydashboard.api.study.type.StudyPlanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateStudyPlanRequestDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private StudyPlanStatus status;
}
