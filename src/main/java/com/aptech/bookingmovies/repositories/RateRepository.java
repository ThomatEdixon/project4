package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Rate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends ElasticsearchRepository<Rate,Integer> {
}
