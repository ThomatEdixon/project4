package com.aptech.bookingmovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForgotPasswordDTO {
    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;
}
