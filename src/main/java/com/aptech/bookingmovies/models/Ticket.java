package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "price_ticket")
    private double priceTicket;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "saet_id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
}
