package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.BillDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Bill;
import com.aptech.bookingmovies.models.Promotion;
import com.aptech.bookingmovies.models.User;
import com.aptech.bookingmovies.repositories.BillRepository;
import com.aptech.bookingmovies.repositories.PromotionRepository;
import com.aptech.bookingmovies.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService{
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Bill findBillByTradingCode(String tradingCode) throws Exception {
        Bill existingBill = billRepository.findByTradingCode(tradingCode);
        if(existingBill == null || !existingBill.isActive()){
            throw new DataNotFoundException("Can not found bill with trading code :" + tradingCode);
        }
        return existingBill;
    }

    @Override
    public List<Bill> findBillByCustomerPhoneNumber(String phoneNumber) throws Exception {
        List<Bill> bills = billRepository.findAll();
        List<Bill> result = new ArrayList<>();
        for(Bill b : bills){
            if(b.getUser().getPhoneNumber().equals(phoneNumber)&& b.isActive()){
                result.add(b);
            }
        }
        return result;
    }
    @Override
    public Bill createBill(BillDTO billDTO) throws Exception {
        User existingUser = userRepository.findById(billDTO.getCustomerId())
                .orElseThrow(()-> new DateTimeException("Can not found user"));
        Promotion promotion = promotionRepository.findById(billDTO.getPromotionId())
                .orElseThrow(()-> new DateTimeException("Can not found promotion"));
        Bill newBill = Bill.builder()
                .totalMoney(billDTO.getTotalMoney())
                .tradingCode(generateTradingCode())
                .createTime(billDTO.getCreateTime())
                .name(billDTO.getName())
                .updateTime(billDTO.getUpdateTime())
                .billStatus(billDTO.getBillStatus())
                .user(existingUser)
                .promotion(promotion)
                .isActive(true)
                .build();
        return billRepository.save(newBill);
    }

    @Override
    public Bill updateBill(int id, BillDTO billDTO) throws Exception {
        User existingUser = userRepository.findById(billDTO.getCustomerId())
                .orElseThrow(()-> new DateTimeException("Can not found user"));
        Promotion promotion = promotionRepository.findById(billDTO.getPromotionId())
                .orElseThrow(()-> new DateTimeException("Can not found promotion"));
        Bill exitingBill = billRepository.findById(id)
                .orElseThrow(()-> new DateTimeException("Can not found bill"));
        exitingBill.setTotalMoney(billDTO.getTotalMoney());
        exitingBill.setBillStatus(billDTO.getBillStatus());
        exitingBill.setCreateTime(billDTO.getCreateTime());
        exitingBill.setUpdateTime(billDTO.getUpdateTime());
        exitingBill.setName((billDTO.getName()));
        exitingBill.setUser(existingUser);
        exitingBill.setPromotion(promotion);
        return billRepository.save(exitingBill);
    }

    @Override
    public String deleteBill(int id) {
        Bill exitingBill = billRepository.findById(id)
                .orElseThrow(()-> new DateTimeException("Can not found bill"));
        exitingBill.setActive(false);
        billRepository.save(exitingBill);
        return "Delete Successfully";
    }
    public String generateTradingCode(){
        String tradingCode = "TC";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        tradingCode +=sb.toString();
        return tradingCode;
    }
}
