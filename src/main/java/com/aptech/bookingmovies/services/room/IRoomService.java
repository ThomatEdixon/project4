package com.aptech.bookingmovies.services.room;

import com.aptech.bookingmovies.dtos.RoomDTO;
import com.aptech.bookingmovies.models.Room;

import java.util.List;

public interface IRoomService {
    List<Room> findAll();
    Room createRoom(RoomDTO roomDTO) throws Exception;
    Room update(int id, RoomDTO roomDTO) throws Exception;
    String delete(int id) throws Exception;
    Room findByRoomName(String name) throws Exception;
    Room findById(int id);
    List<Room> findByCinemaId(int id) throws Exception;
}
