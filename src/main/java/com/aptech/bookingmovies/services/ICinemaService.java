package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.CinemaDTO;
import com.aptech.bookingmovies.models.Cinema;

import java.util.List;

public interface ICinemaService {
    Cinema createCinema(CinemaDTO cinemaDTO) throws Exception;
    Cinema updateCinema(int id,CinemaDTO cinemaDTO) throws Exception;
    List<Cinema> getByName(String name) throws Exception;
    String deleteById(int id) throws Exception;
    List<Cinema> getAll();
    Cinema findById(int id) throws Exception;
}
