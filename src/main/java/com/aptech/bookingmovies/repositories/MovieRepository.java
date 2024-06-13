package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Movie;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends ElasticsearchRepository<Movie,Integer> {
}
