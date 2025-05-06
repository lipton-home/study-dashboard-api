package com.studydashboard.api.redis.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicType {
    STUDY_INVITE("study-invite");
    private final String topic;
}
