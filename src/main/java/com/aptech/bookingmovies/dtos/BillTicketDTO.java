package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillTicketDTO {
    private int quantity;

    @JsonProperty("bill_is")
    private int billId;

    @JsonProperty("ticket_id")
    private int ticketId;
}
