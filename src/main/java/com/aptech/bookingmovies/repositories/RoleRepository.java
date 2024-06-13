package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends ElasticsearchRepository<Role,Integer> {
}
