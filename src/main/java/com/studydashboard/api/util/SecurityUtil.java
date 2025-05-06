package com.studydashboard.api.util;

import com.studydashboard.api.auth.detail.CustomUserDetails;
import com.studydashboard.api.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUser();
        }

        throw new IllegalStateException("인증된 사용자가 아닙니다.");
    }
}