package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "movie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movie_duration")
    private int movieDuration;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "premiere_date")
    private Date premiereDate;
    @Column(name = "description")
    private String description;
    @Column(name = "director")
    private String director;
    @Column(name = "image")
    private String image;
    @Column(name = "hero_image")
    private String heroImage;
    @Column(name = "language")
    private String language;
    @Column(name = "name")
    private String name;
    @Column(name = "trailer")
    private String trailer;
    @Column(name="is_active")
    private boolean isActive;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_movie_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_type_id")
    )
    private Set<MovieType> movieTypes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;
}
