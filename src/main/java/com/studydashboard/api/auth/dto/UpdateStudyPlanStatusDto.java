package com.studydashboard.api.auth.dto;

import com.studydashboard.api.study.type.StudyPlanStatus;
import lombok.Data;

@Data
public class UpdateStudyPlanStatusDto {
    private StudyPlanStatus status;
}
