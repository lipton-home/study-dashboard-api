package com.studydashboard.api.study.repository;

import com.studydashboard.api.study.entity.StudyInvite;
import com.studydashboard.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyInviteRepository extends JpaRepository<StudyInvite, Long> {
    List<StudyInvite> findAllByInviteeAndIsAccepted(User invitee, Boolean isAccepted);
    Long countStudyInvitesByInviteeAndIsRead(User invitee, Boolean isRead);
}
