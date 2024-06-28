package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.RefreshToken;
import com.aptech.bookingmovies.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    List<RefreshToken> findByUser(User user);
    RefreshToken findByToken(String token);
    RefreshToken findByRefreshToken(String token);
}
