package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Promotion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion,Integer> {
}
