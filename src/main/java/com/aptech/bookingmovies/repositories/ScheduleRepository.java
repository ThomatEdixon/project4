package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
}
