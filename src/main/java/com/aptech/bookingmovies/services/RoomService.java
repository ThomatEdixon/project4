package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.RoomDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Cinema;
import com.aptech.bookingmovies.models.Room;
import com.aptech.bookingmovies.repositories.CinemaRepository;
import com.aptech.bookingmovies.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService{
    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;
    @Override
    public List<Room> findAll() {
        Iterable<Room> rooms = roomRepository.findAll();
        List<Room> results = new ArrayList<>();
        for(Room r : rooms){
            if(r.isActive()){
                results.add(r);
            }
        }
        return results;
    }

    @Override
    public Room createRoom(RoomDTO roomDTO) throws Exception{
        Cinema cinema = cinemaRepository.findById(roomDTO.getCinemaId())
                .orElseThrow(()-> new DataNotFoundException("Not found cinema"));
        Room newRoom = Room.builder()
                .capacity(roomDTO.getCapacity())
                .type(roomDTO.getType())
                .description(roomDTO.getDescription())
                .code(generateRoomCode())
                .name(roomDTO.getName())
                .isActive(true)
                .cinema(cinema)
                .build();
        return roomRepository.save(newRoom);
    }

    @Override
    public Room update(int id, RoomDTO roomDTO) throws Exception {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(()->new DateTimeException("Can not found room"));
        Cinema cinema = cinemaRepository.findById(roomDTO.getCinemaId())
                .orElseThrow(()-> new DataNotFoundException("Not found cinema"));
        existingRoom.setCapacity(roomDTO.getCapacity());
        existingRoom.setDescription(roomDTO.getDescription());
        existingRoom.setType(roomDTO.getType());
        existingRoom.setName(roomDTO.getName());
        existingRoom.setCinema(cinema);
        return roomRepository.save(existingRoom);
    }

    @Override
    public String delete(int id) throws Exception{
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(()->new DateTimeException("Can not found room"));
        existingRoom.setActive(false);
        roomRepository.save(existingRoom);
        return "Delete Successfully";
    }

    @Override
    public Room findByRoomName(String name) throws Exception {
        return roomRepository.findByName(name);
    }
    public String generateRoomCode(){
        String roomCode="R";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        roomCode +=sb.toString();
        return roomCode;
    }
}
