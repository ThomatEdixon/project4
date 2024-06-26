package com.aptech.bookingmovies.services.confirmemail;

import com.aptech.bookingmovies.models.ConfirmEmail;
import com.aptech.bookingmovies.models.User;

public interface IConfirmEmailService {
    ConfirmEmail createConfirmEmail(User user);
    boolean checkingConfirmCode(String email ,String confirmCode);
    String sendEmail(String email,String otp);
}
