package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int point;

    @JsonProperty("user_name")
    @NotBlank(message = "User name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("rank_customer_id")
    private int rankCustomerId;

    @JsonProperty("user_status_id")
    private int userStatusId;

    @JsonProperty("role_id")
    private int roleId;
}
