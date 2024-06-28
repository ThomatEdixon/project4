package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name =  "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "capacity")
    private int capacity;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
