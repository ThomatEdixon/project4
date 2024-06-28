package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name =  "seat_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "name_status")
    private String nameStatus;
}
