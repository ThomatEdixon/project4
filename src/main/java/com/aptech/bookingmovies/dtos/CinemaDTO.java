package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Name is required")
    @JsonProperty("name_of_cinema")
    private String nameOfCinema;
}
