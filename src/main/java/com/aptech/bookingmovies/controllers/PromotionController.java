package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.PromotionDTO;
import com.aptech.bookingmovies.models.Promotion;
import com.aptech.bookingmovies.services.promotion.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/promotion")
public class PromotionController {
    private final PromotionService promotionService;
    @GetMapping("/findByRankCustomer")
    public ResponseEntity<?> findByRankCustomer(@RequestParam int rankCustomerId) throws Exception{
        Promotion promotion = promotionService.findPromotionByRankCustomer(rankCustomerId);
        return ResponseEntity.ok(promotion);
    }
    @GetMapping("/findPromotionId")
    public ResponseEntity<?> findPromotionId(@RequestParam int id) throws Exception{
        Promotion promotion = promotionService.findById(id);
        return ResponseEntity.ok(promotion);
    }
    @PostMapping("/createPromotion")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionDTO promotionDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Promotion newPromotion = promotionService.createPromotion(promotionDTO);
            return ResponseEntity.ok(newPromotion);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updatePromotion/{id}")
    public ResponseEntity<?> updatePromotion(@Valid @RequestBody PromotionDTO promotionDTO, @PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Promotion promotion = promotionService.updatePromotion(id,promotionDTO);
            return ResponseEntity.ok(promotion);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletePromotion/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(promotionService.deletePromotion(id));
    }
}
