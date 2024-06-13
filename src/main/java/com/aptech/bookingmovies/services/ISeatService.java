package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.SeatDTO;
import com.aptech.bookingmovies.models.Seat;

import java.util.List;

public interface ISeatService {
    List<Seat> findAll();
    Seat create(SeatDTO seatDTO) throws Exception;
    Seat update(int id,SeatDTO seatDTO) throws Exception;
    String delete(int id) throws Exception;
    Seat findById(int id) throws Exception;
}
