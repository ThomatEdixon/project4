package com.aptech.bookingmovies.services.promotion;

import com.aptech.bookingmovies.dtos.PromotionDTO;
import com.aptech.bookingmovies.models.Promotion;

import java.util.List;

public interface IPromotionService {
    Promotion findPromotionByRankCustomer(int rankCustomerId);
    Promotion createPromotion(PromotionDTO promotionDTO) throws Exception;
    Promotion updatePromotion(int id, PromotionDTO promotionDTO) throws Exception;
    String deletePromotion(int id) throws Exception;
    List<Promotion> findAll();
    Promotion findById(int id) throws Exception;
}
