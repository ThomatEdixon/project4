package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name =  "movie_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movie_type_name")
    private String movieTypeName;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToMany(mappedBy = "movieTypes", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Movie> movies = new HashSet<>();
}
