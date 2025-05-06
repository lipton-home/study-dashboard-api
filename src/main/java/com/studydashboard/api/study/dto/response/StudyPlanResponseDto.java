package com.studydashboard.api.study.dto.response;

import com.studydashboard.api.study.type.StudyPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanResponseDto {
    private Long id;
    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private StudyPlanStatus status;
}
