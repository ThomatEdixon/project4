package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Room findByName(String name);
}
