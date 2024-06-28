package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
