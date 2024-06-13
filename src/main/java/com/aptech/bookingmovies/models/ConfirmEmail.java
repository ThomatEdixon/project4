package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Entity
@Data
@Document(indexName = "confirm_email")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "expired_time")
    private LocalDateTime expriedTime;
    @Column(name = "confirm_code")
    private String confirmCode;
    @Column(name = "is_confirm")
    private boolean isConfirm;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
