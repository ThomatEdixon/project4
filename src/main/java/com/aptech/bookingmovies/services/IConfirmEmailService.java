package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.models.ConfirmEmail;
import com.aptech.bookingmovies.models.User;

public interface IConfirmEmailService {
    ConfirmEmail createConfirmEmail(User user);
    boolean checkingConfirmCode(String confirmCode);
    String sendEmail(String email,String otp);
}
