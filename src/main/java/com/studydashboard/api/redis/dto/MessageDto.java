package com.studydashboard.api.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    @Default
    private String messageId = UUID.randomUUID().toString();
    private String email;
    private String studyTitle;
    private String inviter;
}
