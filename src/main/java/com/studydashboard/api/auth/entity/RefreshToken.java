package com.studydashboard.api.auth.entity;

import com.studydashboard.api.common.BaseEntity;
import com.studydashboard.api.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RefreshToken")
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;
}
