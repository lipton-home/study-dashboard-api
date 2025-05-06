package com.studydashboard.api.study.mapper;

import com.studydashboard.api.study.dto.StudyResponseDto;
import com.studydashboard.api.study.dto.response.StudyPlanResponseDto;
import com.studydashboard.api.study.dto.response.StudyUserResponseDto;
import com.studydashboard.api.study.entity.Study;
import com.studydashboard.api.study.entity.StudyPlan;
import com.studydashboard.api.study.entity.StudyUser;

public class StudyMapper {
    public static StudyResponseDto toDto(final Study study){
        return StudyResponseDto.builder()
                .id(study.getId())
                .title(study.getTitle())
                .description(study.getDescription())
                .status(study.getStatus())
                .type(study.getType())
                .startDate(study.getStartDate())
                .endDate(study.getEndDate())
                .build();
    }
    public static StudyUserResponseDto toDto(final StudyUser studyUser) {
        return StudyUserResponseDto.builder()
                .userId(studyUser.getStudyUserId().getUserId())
                .studyId(studyUser.getStudyUserId().getStudyId())
                .build();
    }
    public static StudyPlanResponseDto toDto(final StudyPlan studyPlane){
        return StudyPlanResponseDto.builder()
                .title(studyPlane.getTitle())
                .id(studyPlane.getId())
                .description(studyPlane.getDescription())
                .startDate(studyPlane.getStartDate())
                .endDate(studyPlane.getEndDate())
                .status(studyPlane.getStatus())
                .build();
    }
}
