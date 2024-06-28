package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "number")
    private int number;
    @Column(name = "line")
    private String line;
    @Column(name = "seat_type")
    private String seatType;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "seat_status_id")
    private SeatStatus seatStatus;
}
