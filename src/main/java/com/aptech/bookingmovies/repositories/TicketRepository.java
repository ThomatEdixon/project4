package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Ticket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    Ticket findByCode(String code);
}
