package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema,Integer> {

}
