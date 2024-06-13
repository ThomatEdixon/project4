package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.UserStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus,Integer> {
}
