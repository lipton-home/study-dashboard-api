package com.studydashboard.api.study.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class InviteStudyUsersRequestDto {
    private List<String> emails;
}
