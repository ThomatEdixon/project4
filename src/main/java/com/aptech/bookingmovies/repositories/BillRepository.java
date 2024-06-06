package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    Bill findByTradingCode(String tradingCode);

}
