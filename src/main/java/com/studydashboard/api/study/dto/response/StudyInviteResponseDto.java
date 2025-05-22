package com.studydashboard.api.study.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyInviteResponseDto {
    private Long id;
    private Long studyId;
    private String studyName;
    private Long inviterId;
    private String inviterName;
    private Boolean isAccepted;
}
