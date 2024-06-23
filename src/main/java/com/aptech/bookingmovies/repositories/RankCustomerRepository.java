package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.RankCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankCustomerRepository extends JpaRepository<RankCustomer,Integer> {
}
