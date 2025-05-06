package com.studydashboard.api.study.entity;

import com.studydashboard.api.common.BaseEntity;
import com.studydashboard.api.study.data.StudyUserId;
import com.studydashboard.api.study.type.StudyUserStatus;
import com.studydashboard.api.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StudyUser")
public class StudyUser extends BaseEntity {
    @EmbeddedId
    private StudyUserId studyUserId;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("studyId")
    private Study study;

    @Enumerated(EnumType.STRING)
    private StudyUserStatus status;

}
