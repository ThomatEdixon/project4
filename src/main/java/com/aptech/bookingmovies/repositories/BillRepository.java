package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Bill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BillRepository extends ElasticsearchRepository<Bill,Integer> {
    Bill findByTradingCode(String tradingCode);

}
