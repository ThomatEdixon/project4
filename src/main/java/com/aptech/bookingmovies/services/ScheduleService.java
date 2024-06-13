package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.ScheduleDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.models.Room;
import com.aptech.bookingmovies.models.Schedule;
import com.aptech.bookingmovies.repositories.MovieRepository;
import com.aptech.bookingmovies.repositories.RoomRepository;
import com.aptech.bookingmovies.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    @Override
    public List<Schedule> findByMovieName(String movieName) {
        List<Schedule> schedules = this.findAll();
        List<Schedule> result = new ArrayList<>();
        for(Schedule s : schedules){
            if(s.getMovie().getName().contains(movieName)){
                result.add(s);
            }
        }
        return schedules;
    }

    @Override
    public Schedule createSchedule(ScheduleDTO scheduleDTO) throws Exception {
        Room existingRoom = roomRepository.findById(scheduleDTO.getRoomId())
                .orElseThrow(()-> new DataNotFoundException("Can not found room"));
        Movie existingMovie = movieRepository.findById(scheduleDTO.getMovieId())
                .orElseThrow(()->new DataNotFoundException("Can not found movie"));
        Schedule newSchedule = Schedule.builder()
                .price(scheduleDTO.getPrice())
                .startAt(scheduleDTO.getStartAt())
                .endAt(scheduleDTO.getEndAt())
                .code(scheduleDTO.getCode())
                .name(scheduleDTO.getName())
                .movie(existingMovie)
                .room(existingRoom)
                .isActive(true)
                .build();
        return scheduleRepository.save(newSchedule);
    }

    @Override
    public Schedule updateSchedule(int id, ScheduleDTO scheduleDTO) throws Exception {
        Room existingRoom = roomRepository.findById(scheduleDTO.getRoomId())
                .orElseThrow(()-> new DataNotFoundException("Can not found room"));
        Movie existingMovie = movieRepository.findById(scheduleDTO.getMovieId())
                .orElseThrow(()->new DataNotFoundException("Can not found movie"));
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found schedule"));
        existingSchedule.setPrice(scheduleDTO.getPrice());
        existingSchedule.setStartAt(scheduleDTO.getStartAt());
        existingSchedule.setEndAt(scheduleDTO.getEndAt());
        existingSchedule.setCode(scheduleDTO.getCode());
        existingSchedule.setName(scheduleDTO.getName());
        existingSchedule.setMovie(existingMovie);
        existingSchedule.setRoom(existingRoom);
        return scheduleRepository.save(existingSchedule);
    }

    @Override
    public String deleteSchedule(int id) throws Exception {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found schedule"));
        existingSchedule.setActive(false);
        scheduleRepository.save(existingSchedule);
        return "Delete Successfully";
    }

    @Override
    public List<Schedule> findAll() {
        Iterable<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> results = new ArrayList<>();
        for(Schedule s: schedules){
            if(s.isActive()){
                results.add(s);
            }
        }
        return results;
    }
}
