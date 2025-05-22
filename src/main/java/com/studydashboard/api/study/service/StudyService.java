package com.studydashboard.api.study.service;

import com.studydashboard.api.redis.dto.MessageDto;
import com.studydashboard.api.redis.publisher.RedisPublisher;
import com.studydashboard.api.redis.type.TopicType;
import com.studydashboard.api.study.data.StudyUserId;
import com.studydashboard.api.study.dto.StudyResponseDto;
import com.studydashboard.api.study.dto.request.*;
import com.studydashboard.api.study.dto.response.StudyInviteResponseDto;
import com.studydashboard.api.study.dto.response.StudyPlanResponseDto;
import com.studydashboard.api.study.dto.response.StudyUserResponseDto;
import com.studydashboard.api.study.entity.Study;
import com.studydashboard.api.study.entity.StudyInvite;
import com.studydashboard.api.study.entity.StudyPlan;
import com.studydashboard.api.study.entity.StudyUser;
import com.studydashboard.api.study.mapper.StudyMapper;
import com.studydashboard.api.study.repository.StudyInviteRepository;
import com.studydashboard.api.study.repository.StudyPlanRepository;
import com.studydashboard.api.study.repository.StudyRepository;
import com.studydashboard.api.study.repository.StudyUserRepository;
import com.studydashboard.api.study.type.StudyPlanStatus;
import com.studydashboard.api.study.type.StudyUserStatus;
import com.studydashboard.api.user.entity.User;
import com.studydashboard.api.user.service.UserService;
import com.studydashboard.api.util.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyUserRepository studyUserRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final StudyInviteRepository studyInviteRepository;
    private final UserService userService;
    private final RedisPublisher redisPublisher;

    @Transactional
    public List<StudyInviteResponseDto> findInvites(){
        User user = SecurityUtil.getCurrentUser();
        List<StudyInvite> unreadInvites = studyInviteRepository.findAllByInviteeAndIsAccepted(user, false);
        List<StudyInviteResponseDto> studyInvites = unreadInvites
                .stream()
                .map(StudyMapper::toDto)
                .toList();
        unreadInvites.forEach(studyInvite -> studyInvite.setIsRead(true));
        studyInviteRepository.saveAll(unreadInvites);
        return studyInvites;
    }

    @Transactional
    public Long countNewInvites() {
        User user = SecurityUtil.getCurrentUser();
        return studyInviteRepository.countStudyInvitesByInviteeAndIsRead(user, false);
    }

    @Transactional
    public StudyResponseDto create(final CreateStudyRequestDto dto) {
        User user = SecurityUtil.getCurrentUser();
        Study study = Study.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .type(dto.getType())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        studyRepository.save(study);
        StudyUser studyUser = StudyUser.builder()
                .studyUserId(
                        StudyUserId.builder()
                                .studyId(study.getId())
                                .userId(user.getId())
                                .build())
                .study(study)
                .user(user)
                .build();
        studyUserRepository.save(studyUser);
        return StudyMapper.toDto(study);
    }

    @Transactional
    public List<StudyUserResponseDto> invite(final Long id, final InviteStudyUsersRequestDto dto) {
        Study study = studyRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        User user = SecurityUtil.getCurrentUser();
        List<User> invitedUsers = userService.findUsersByEmails(dto.getEmails());
        List<StudyUser> studyUsers = invitedUsers.stream()
                .map((invitedUser) -> StudyUser.builder()
                        .studyUserId(StudyUserId.builder()
                                .studyId(study.getId())
                                .userId(invitedUser.getId())
                                .build())
                        .study(study)
                        .user(invitedUser)
                        .build()).toList();
        List<MessageDto> messages = studyUsers.stream().map((studyUser) ->
                MessageDto.builder()
                        .email(studyUser.getUser().getEmail())
                        .inviter(user.getEmail())
                        .studyTitle(study.getTitle())
                        .build()
        ).toList();

        messages.forEach((message) -> redisPublisher.publish(ChannelTopic.of(TopicType.STUDY_INVITE.getTopic()), message));

        studyUserRepository.saveAll(studyUsers);
        return studyUsers.stream().map(StudyMapper::toDto).toList();
    }

    @Transactional
    public Page<StudyResponseDto> findAll(final Pageable pageable) {
        User user = SecurityUtil.getCurrentUser();
        Page<StudyUser> studyUsers = studyUserRepository.findStudyUsersByUser(user, pageable);
        return studyUsers.map((studyUser -> StudyMapper.toDto(studyUser.getStudy())));
    }

    @Transactional
    public StudyResponseDto find(final Long id){
        Study study = studyRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        return StudyMapper.toDto(study);
    }

    @Transactional
    public StudyResponseDto update(final Long id, final UpdateStudyRequestDto dto) {
        Study study = studyRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        if (dto.getTitle() != null) {
            study.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            study.setDescription(dto.getDescription());
        }
        if (dto.getType() != null) {
            study.setType(dto.getType());
        }
        if (dto.getStatus() != null) {
            study.setStatus(dto.getStatus());
        }
        if(dto.getStartDate() != null){
            study.setStartDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null){
            study.setEndDate(dto.getEndDate());
        }
        studyRepository.save(study);
        return StudyMapper.toDto(study);
    }

    @Transactional
    public StudyResponseDto delete(final Long id) {
        Study study = studyRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        studyUserRepository.deleteAllByStudy(study);
        studyPlanRepository.deleteAllByStudy(study);
        studyRepository.delete(study);
        return StudyMapper.toDto(study);

    }

    @Transactional
    public StudyPlanResponseDto createPlan(final Long id, final CreateStudyPlanRequestDto dto){
        Study study = studyRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        StudyPlan studyPlan = StudyPlan.builder()
                .description(dto.getDescription())
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .study(study)
                .build();
        studyPlanRepository.save(studyPlan);
        return StudyMapper.toDto(studyPlan);
    }

    @Transactional
    public List<StudyPlanResponseDto> getPlans(final Long id){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        List<StudyPlan> studyPlans = studyPlanRepository.findAllByStudy(study);
        return studyPlans.stream().map(StudyMapper::toDto).toList();
    }
    public StudyPlanResponseDto updatePlanStatus(Long studyId, Long planId, StudyPlanStatus status) {
        StudyPlan plan = studyPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Study plan not found"));

        if (!plan.getStudy().getId().equals(studyId)) {
            throw new IllegalArgumentException("Study plan does not belong to the study");
        }

        plan.setStatus(status);
        return StudyMapper.toDto(studyPlanRepository.save(plan));
    }

    @Transactional
    public StudyPlanResponseDto updatePlan(final Long studyId, final Long id, final UpdateStudyPlanRequestDto dto) {
        Study study = studyRepository.findById(studyId).
                orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        StudyPlan studyPlan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계획입니다.."));

        if (dto.getTitle() != null) {
            studyPlan.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            studyPlan.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            studyPlan.setStatus(dto.getStatus());
        }
        if(dto.getStartDate() != null){
            studyPlan.setStartDate(dto.getStartDate());
        }
        if(dto.getEndDate() != null){
            studyPlan.setEndDate(dto.getEndDate());
        }
        studyPlanRepository.save(studyPlan);
        return StudyMapper.toDto(studyPlan);
    }

    @Transactional
    public StudyPlanResponseDto deletePlan(final Long studyId, final Long id) {
        Study study = studyRepository.findById(studyId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));
        StudyPlan studyPlan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계획입니다.."));
        studyPlanRepository.delete(studyPlan);
        return StudyMapper.toDto(studyPlan);

    }
}
