package com.studydashboard.api.study.entity;

import com.studydashboard.api.common.BaseEntity;
import com.studydashboard.api.study.type.StudyStatus;
import com.studydashboard.api.study.type.StudyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Study")
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private StudyType type;
    @Enumerated(EnumType.STRING)
    private StudyStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
