package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.util.Date;

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
    @ManyToOne
    @JoinColumn(name = "movie_type_id")
    private MovieType movieType;
    @ManyToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;
}
