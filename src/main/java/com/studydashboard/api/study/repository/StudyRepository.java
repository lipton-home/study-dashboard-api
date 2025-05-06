package com.studydashboard.api.study.repository;

import com.studydashboard.api.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
