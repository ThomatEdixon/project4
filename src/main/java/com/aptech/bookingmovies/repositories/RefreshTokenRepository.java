package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.RefreshToken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
}
