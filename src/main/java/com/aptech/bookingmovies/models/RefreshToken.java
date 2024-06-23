package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name =  "refresh_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "token")
    private String token;
    @Column(name = "expired_time")
    private LocalDateTime expiredTime;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "expired_time_refresh_token")
    private LocalDateTime expiredTimeRefreshToken;
    @ManyToOne
    @JoinColumn
    private User user;
}
