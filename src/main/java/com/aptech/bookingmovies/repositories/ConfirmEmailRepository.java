package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.ConfirmEmail;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmEmailRepository extends ElasticsearchRepository<ConfirmEmail,Integer> {
    ConfirmEmail findByConfirmCode(String confirmCode);

}
