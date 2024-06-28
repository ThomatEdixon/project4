package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name =  "rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
}
