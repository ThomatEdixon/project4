package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType,Integer> {
}
