package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Integer> {
}
