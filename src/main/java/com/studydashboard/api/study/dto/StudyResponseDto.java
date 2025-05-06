package com.studydashboard.api.study.dto;

import com.studydashboard.api.study.type.StudyPlanStatus;
import com.studydashboard.api.study.type.StudyStatus;
import com.studydashboard.api.study.type.StudyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyResponseDto {
    private Long id;
    private String title;
    private String description;
    private StudyType type;
    private StudyStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
