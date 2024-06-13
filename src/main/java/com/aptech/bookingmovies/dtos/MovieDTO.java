package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private int movieDuration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date premiereDate;

    private String description;

    private String director;

    private String image;

    private String heroImage;

    private String language;

    private String name;

    private String trailer;

    private int movieTypeId;

    private int rateId;
    private MultipartFile file;

}
