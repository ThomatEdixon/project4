package com.aptech.bookingmovies.services.promotion;

import com.aptech.bookingmovies.dtos.PromotionDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Promotion;
import com.aptech.bookingmovies.models.RankCustomer;
import com.aptech.bookingmovies.repositories.PromotionRepository;
import com.aptech.bookingmovies.repositories.RankCustomerRepository;
import com.aptech.bookingmovies.services.promotion.IPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService implements IPromotionService {
    private final PromotionRepository promotionRepository;
    private final RankCustomerRepository rankCustomerRepository;

    @Override
    public Promotion findPromotionByRankCustomer(int rankCustomerId) {
        List<Promotion> promotions = this.findAll();
        Promotion promotion = new Promotion();
        for(Promotion p : promotions){
            if(p.getRankCustomer().getId() == rankCustomerId){
                promotion = p;
            }
        }
        return promotion;
    }

    @Override
    public Promotion createPromotion(PromotionDTO promotionDTO) throws Exception {
        RankCustomer rankCustomer = rankCustomerRepository.findById(promotionDTO.getRankCustomerId())
                .orElseThrow(()->new DataNotFoundException("Can not found rank"));
        Promotion promotion = Promotion.builder()
                .percent(promotionDTO.getPercent())
                .quantity(promotionDTO.getQuantity())
                .type(promotionDTO.getType())
                .startTime(promotionDTO.getStartTime())
                .endTime(promotionDTO.getEndTime())
                .description(promotionDTO.getDescription())
                .name(promotionDTO.getName())
                .isActive(true)
                .rankCustomer(rankCustomer)
                .build();
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(int id, PromotionDTO promotionDTO) throws Exception {
        RankCustomer rankCustomer = rankCustomerRepository.findById(promotionDTO.getRankCustomerId())
                .orElseThrow(()->new DataNotFoundException("Can not found rank"));
        Promotion existingPromotion = promotionRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found promotion"));
        existingPromotion.setPercent(promotionDTO.getPercent());
        existingPromotion.setQuantity(promotionDTO.getQuantity());
        existingPromotion.setType(promotionDTO.getType());
        existingPromotion.setStartTime(promotionDTO.getStartTime());
        existingPromotion.setEndTime(promotionDTO.getEndTime());
        existingPromotion.setDescription(promotionDTO.getDescription());
        existingPromotion.setName(promotionDTO.getName());
        existingPromotion.setRankCustomer(rankCustomer);
        return promotionRepository.save(existingPromotion);
    }

    @Override
    public String deletePromotion(int id) throws Exception{
        Promotion existingPromotion = promotionRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found promotion"));
        existingPromotion.setActive(false);
        promotionRepository.save(existingPromotion);
        return "Delete Successfully";
    }

    @Override
    public List<Promotion> findAll() {
        Iterable<Promotion> promotions = promotionRepository.findAll();
        List<Promotion> result = new ArrayList<>();
        for(Promotion p: promotions){
            if(p.isActive()){
                result.add(p);
            }
        }
        return result;
    }

    @Override
    public Promotion findById(int id) throws Exception{
        Promotion existingPromotion = promotionRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Can not found promotion"));
        return existingPromotion;
    }
}
