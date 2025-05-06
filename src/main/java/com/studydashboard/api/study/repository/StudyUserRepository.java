package com.studydashboard.api.study.repository;

import com.studydashboard.api.study.data.StudyUserId;
import com.studydashboard.api.study.entity.Study;
import com.studydashboard.api.study.entity.StudyUser;
import com.studydashboard.api.study.type.StudyUserStatus;
import com.studydashboard.api.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyUserRepository extends JpaRepository<StudyUser, StudyUserId> {
    List<StudyUser> findStudyUsersByUser(User user);
    List<StudyUser> findStudyUsersByUserAndStatus(User user, StudyUserStatus status);
    Page<StudyUser> findStudyUsersByUser(User user, Pageable pageable);
    List<StudyUser> deleteAllByStudy(Study study);
}
