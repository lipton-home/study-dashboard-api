package com.studydashboard.api.study.controller;

import com.studydashboard.api.auth.dto.UpdateStudyPlanStatusDto;
import com.studydashboard.api.study.dto.StudyResponseDto;
import com.studydashboard.api.study.dto.request.*;
import com.studydashboard.api.study.dto.response.StudyInviteResponseDto;
import com.studydashboard.api.study.dto.response.StudyPlanResponseDto;
import com.studydashboard.api.study.dto.response.StudyUserResponseDto;
import com.studydashboard.api.study.service.StudyService;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/studies")
@AllArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @GetMapping("invites")
    public ResponseEntity<List<StudyInviteResponseDto>> findInvites(){
        return ResponseEntity.ok(studyService.findInvites());
    }
    @GetMapping("invites/new")
    public ResponseEntity<Long> countNewInvites() {
        return ResponseEntity.ok(studyService.countNewInvites());
    }
    @PostMapping
    public ResponseEntity<StudyResponseDto> create(@RequestBody final CreateStudyRequestDto dto) {
        return ResponseEntity.ok(studyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<StudyResponseDto>> findAll(
            @ParameterObject
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(studyService.findAll(pageable));
    }

    @PostMapping("{studyId}/invites")
    public ResponseEntity<List<StudyUserResponseDto>> invite(
            @PathVariable final Long studyId,
            @RequestBody final InviteStudyUsersRequestDto dto
    ) {
        return ResponseEntity.ok(studyService.invite(studyId, dto));
    }
    @GetMapping("{studyId}")
    public ResponseEntity<StudyResponseDto> find(@PathVariable final Long studyId) {
        return ResponseEntity.ok(studyService.find(studyId));
    }

    @PatchMapping("{studyId}")
    public ResponseEntity<StudyResponseDto> update(@PathVariable final Long studyId, @RequestBody final UpdateStudyRequestDto dto) {
        return ResponseEntity.ok(studyService.update(studyId, dto));
    }

    @PatchMapping("{studyId}/plans/{planId}/status")
    public ResponseEntity<StudyPlanResponseDto> updatePlanStatus(
            @PathVariable final Long studyId,
            @PathVariable final Long planId,
            @RequestBody final UpdateStudyPlanStatusDto dto
    ) {
        return ResponseEntity.ok(studyService.updatePlanStatus(studyId, planId, dto.getStatus()));
    }

    @DeleteMapping("{studyId}")
    public ResponseEntity<StudyResponseDto> delete(@PathVariable final Long studyId) {
        return ResponseEntity.ok(studyService.delete(studyId));
    }

    @GetMapping("{studyId}/plans")
    public ResponseEntity<List<StudyPlanResponseDto>> getPlans(@PathVariable final Long studyId) {
        return ResponseEntity.ok(studyService.getPlans(studyId));
    }

    @PostMapping("{studyId}/plans")
    public ResponseEntity<StudyPlanResponseDto> createPlane(@PathVariable final Long studyId, @RequestBody final CreateStudyPlanRequestDto dto) {
        return ResponseEntity.ok(studyService.createPlan(studyId,dto));
    }

    @PatchMapping("{studyId}/plans/{planId}")
    public ResponseEntity<StudyPlanResponseDto> updatePlane(@PathVariable final Long studyId, @PathVariable final Long planId, @RequestBody final UpdateStudyPlanRequestDto dto) {
        return ResponseEntity.ok(studyService.updatePlan(studyId,planId,dto));
    }

    @DeleteMapping("{studyId}/plans/{planId}")
    public ResponseEntity<StudyPlanResponseDto> deletePlane(@PathVariable final Long studyId, @PathVariable final Long planId) {
        return ResponseEntity.ok(studyService.deletePlan(studyId,planId));
    }
}
