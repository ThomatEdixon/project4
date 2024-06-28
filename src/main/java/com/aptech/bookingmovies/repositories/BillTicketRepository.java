package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.BillTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillTicketRepository extends JpaRepository<BillTicket,Integer> {
}
