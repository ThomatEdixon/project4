package com.aptech.bookingmovies.services.billticket;

import com.aptech.bookingmovies.dtos.BillTicketDTO;
import com.aptech.bookingmovies.models.Bill;
import com.aptech.bookingmovies.models.BillTicket;

import java.util.List;

public interface IBillTicketService {
    List<BillTicket> findBillTicketByCustomerPhoneNumber(String phoneNumber);
    BillTicket createBillTicket(BillTicketDTO billTicketDTO) throws Exception;
    BillTicket updateBillTicket(int id, BillTicketDTO billTicketDTO) throws  Exception;
    String deleteBillTicket(int id) throws Exception;
    List<BillTicket> findAll();
    BillTicket findById(int id) throws Exception;
}
