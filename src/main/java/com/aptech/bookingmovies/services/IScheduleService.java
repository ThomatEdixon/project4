package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.ScheduleDTO;
import com.aptech.bookingmovies.models.Schedule;

import java.util.List;

public interface IScheduleService {
    List<Schedule> findByMovieName(String movieName);
    Schedule createSchedule(ScheduleDTO scheduleDTO) throws Exception;

    Schedule updateSchedule(int id,ScheduleDTO scheduleDTO) throws Exception;

    String deleteSchedule(int id) throws Exception;
    List<Schedule> findAll();
}
