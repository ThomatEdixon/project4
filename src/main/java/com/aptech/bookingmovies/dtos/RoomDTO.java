package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private int capacity;

    private String type;

    private String description;

    private String name;

    @JsonProperty("cinema_id")
    private int cinemaId;
}
