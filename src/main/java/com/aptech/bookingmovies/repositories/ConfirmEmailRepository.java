package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail,Integer> {
}
