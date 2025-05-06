package com.studydashboard.api.user.service;

import com.studydashboard.api.user.dto.CreateUserRequestDto;
import com.studydashboard.api.user.dto.FindUserRequestDto;
import com.studydashboard.api.user.entity.User;
import com.studydashboard.api.user.repository.UserRepository;
import com.studydashboard.api.user.type.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    public User create(final CreateUserRequestDto dto){
        String encodePassword = bCryptPasswordEncoder.encode(dto.password());

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(encodePassword)
                .role(UserRole.USER)
                .build();
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User find(final FindUserRequestDto dto){
        User user = userRepository.findUserByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!bCryptPasswordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    public List<User> findUsersByEmails(List<String> emails){
        return userRepository.findUsersByEmailIsIn(emails);
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    }
}
