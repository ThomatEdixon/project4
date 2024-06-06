package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Table(name = "cinema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;
    @Column(name = "name_of_cinema")
    private String nameOfCinema;
    @Column(name="is_active")
    private boolean isActive;
}
