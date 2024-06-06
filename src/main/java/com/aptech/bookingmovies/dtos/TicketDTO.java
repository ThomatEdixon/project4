package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    @JsonProperty("price_ticket")
    private double priceTicket;
    @JsonProperty("seat_id")
    private int seatId;
    @JsonProperty("schedule_id")
    private int scheduleId;
}
