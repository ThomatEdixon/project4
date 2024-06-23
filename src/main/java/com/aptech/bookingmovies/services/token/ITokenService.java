package com.aptech.bookingmovies.services.token;

import com.aptech.bookingmovies.models.RefreshToken;
import com.aptech.bookingmovies.models.User;

public interface ITokenService {
    RefreshToken addToken(User user, String token);
    RefreshToken refreshToken(String refreshToken, User user) throws Exception;
}
