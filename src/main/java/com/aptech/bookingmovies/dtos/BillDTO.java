package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    @JsonProperty("total_money")
    private double totalMoney;

    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @JsonProperty("update_time")
    private LocalDateTime updateTime;

    private String name;

    @JsonProperty("bill_status")
    private String billStatus;

    @JsonProperty("customer_id")
    private int customerId;

    @JsonProperty("promotion_id")
    private int promotionId;

}
