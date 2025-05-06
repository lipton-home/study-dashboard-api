package com.studydashboard.api.study.entity;

import com.studydashboard.api.common.BaseEntity;
import com.studydashboard.api.study.type.StudyPlanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "StudyPlane")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudyPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String description;


    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private StudyPlanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studyId")
    private Study study;

}
