package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    private int number;

    private String line;

    @JsonProperty("seat_type")
    private String seatType;

    @JsonProperty("room_id")
    private int roomId;

    @JsonProperty("seat_status_id")
    private int seatStatusId;
}
