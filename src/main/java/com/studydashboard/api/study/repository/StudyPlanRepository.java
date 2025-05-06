package com.studydashboard.api.study.repository;

import com.studydashboard.api.study.entity.Study;
import com.studydashboard.api.study.entity.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, Long> {
    List<StudyPlan> deleteAllByStudy(Study study);
    List<StudyPlan> findAllByStudy(Study study);
}
