package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    @JsonProperty("movie_duration")
    private int movieDuration;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("premiere_date")
    private Date premiereDate;

    private String description;

    private String director;

    private String image;

    @JsonProperty("hero_image")
    private String heroImage;

    private String language;

    private String name;

    private String trailer;

    @JsonProperty("movie_type_id")
    private int movieTypeId;

    @JsonProperty("rate_id")
    private int rateId;

}
