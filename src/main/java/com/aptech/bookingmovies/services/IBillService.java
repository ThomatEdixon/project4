package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.BillDTO;
import com.aptech.bookingmovies.models.Bill;

import java.util.List;

public interface IBillService {
    Bill findBillByTradingCode(String TradingCode) throws Exception;
    List<Bill> findBillByCustomerPhoneNumber(String phoneNumber) throws Exception;
    Bill createBill(BillDTO billDTO) throws Exception;
    Bill updateBill(int id, BillDTO billDTO) throws Exception;
    String deleteBill(int id);
}
