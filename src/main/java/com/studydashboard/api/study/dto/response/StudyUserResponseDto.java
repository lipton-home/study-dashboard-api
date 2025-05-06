package com.studydashboard.api.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyUserResponseDto {
    private Long userId;
    private Long studyId;
}
