package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.SeatStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatStatusRepository extends JpaRepository<SeatStatus, Integer> {
}
