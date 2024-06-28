package com.aptech.bookingmovies.services.seat;

import com.aptech.bookingmovies.dtos.SeatDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Room;
import com.aptech.bookingmovies.models.Seat;
import com.aptech.bookingmovies.models.SeatStatus;
import com.aptech.bookingmovies.repositories.RoomRepository;
import com.aptech.bookingmovies.repositories.SeatRepository;
import com.aptech.bookingmovies.repositories.SeatStatusRepository;
import com.aptech.bookingmovies.services.seat.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final RoomRepository roomRepository;
    private final SeatStatusRepository seatStatusRepository;
    private final SeatRepository seatRepository;

    @Override
    public List<Seat> findAll() {
        Iterable<Seat> seats = seatRepository.findAll();
        List<Seat> result = new ArrayList<>();
        for(Seat s: seats){
            if(s.isActive()){
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public Seat create(SeatDTO seatDTO) throws Exception {
        Room existingRoom = roomRepository.findById(seatDTO.getRoomId())
                .orElseThrow(()->new DateTimeException("Can not found room"));
        SeatStatus seatStatus= seatStatusRepository.findById(seatDTO.getSeatStatusId())
                .orElseThrow(()->new DateTimeException("Can not found room"));
        Seat newSeat = Seat.builder()
                .number(seatDTO.getNumber())
                .line(seatDTO.getLine())
                .seatType(seatDTO.getSeatType())
                .room(existingRoom)
                .seatStatus(seatStatus)
                .isActive(true)
                .build();
        return seatRepository.save(newSeat);
    }

    @Override
    public Seat update(int id, SeatDTO seatDTO) throws Exception {
        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found seat"));
        Room existingRoom = roomRepository.findById(seatDTO.getRoomId())
                .orElseThrow(()->new DateTimeException("Can not found room"));
        SeatStatus seatStatus= seatStatusRepository.findById(seatDTO.getSeatStatusId())
                .orElseThrow(()->new DateTimeException("Can not found room"));
        existingSeat.setNumber(seatDTO.getNumber());
        existingSeat.setLine(seatDTO.getLine());
        existingSeat.setSeatType(seatDTO.getSeatType());
        existingSeat.setRoom(existingRoom);
        existingSeat.setSeatStatus(seatStatus);
        return seatRepository.save(existingSeat);
    }

    @Override
    public String delete(int id) throws Exception {
        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found seat"));
        existingSeat.setActive(false);
        seatRepository.save(existingSeat);
        return "Delete Successfully";
    }

    @Override
    public Seat findById(int id) throws Exception{
        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found seat"));
        return existingSeat;
    }
}
