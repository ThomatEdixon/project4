package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.BillTicketDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Bill;
import com.aptech.bookingmovies.models.BillTicket;
import com.aptech.bookingmovies.models.Ticket;
import com.aptech.bookingmovies.repositories.BillRepository;
import com.aptech.bookingmovies.repositories.BillTicketRepository;
import com.aptech.bookingmovies.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BillTicketService implements IBillTicketService{
    private final BillRepository billRepository;
    private final TicketRepository ticketRepository;
    private final BillTicketRepository billTicketRepository;
    @Override
    public List<BillTicket> findBillTicketByCustomerPhoneNumber(String phoneNumber) {
        List<Bill> bills = billRepository.findAll();
        List<Bill> result = new ArrayList<>();
        for(Bill b : bills){
            if(b.getUser().getPhoneNumber().equals(phoneNumber)){
                result.add(b);
            }
        }
        List<BillTicket> billTickets = billTicketRepository.findAll();
        List<BillTicket> resultBillTickets = new ArrayList<>();
        for(Bill bill : result){
            for (int i =0 ;i<billTickets.size();i++){
                if(billTickets.get(i).getBill().getUser().getPhoneNumber().equals(bill.getUser().getPhoneNumber())){
                    resultBillTickets.add(billTickets.get(i));
                }
            }
        }
        return resultBillTickets;
    }

    @Override
    public BillTicket createBillTicket(BillTicketDTO billTicketDTO) throws Exception {
        Bill existingBill = billRepository.findById(billTicketDTO.getBillId())
                .orElseThrow(()-> new DataNotFoundException("Can not found bill"));
        Ticket ticket = ticketRepository.findById(billTicketDTO.getTicketId())
                .orElseThrow(()-> new DataNotFoundException("Can not found ticket"));
        BillTicket newBillTicket = BillTicket.builder()
                .quantity(billTicketDTO.getQuantity())
                .bill(existingBill)
                .ticket(ticket)
                .build();
        return billTicketRepository.save(newBillTicket);
    }

    @Override
    public BillTicket updateBillTicket(int id, BillTicketDTO billTicketDTO) throws Exception {
        Bill existingBill = billRepository.findById(billTicketDTO.getBillId())
                .orElseThrow(()-> new DataNotFoundException("Can not found bill"));
        Ticket ticket = ticketRepository.findById(billTicketDTO.getTicketId())
                .orElseThrow(()-> new DataNotFoundException("Can not found ticket"));
        BillTicket existingBillTicket = billTicketRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found bill ticket"));
        existingBillTicket.setQuantity(billTicketDTO.getQuantity());
        existingBillTicket.setTicket(ticket);
        existingBillTicket.setBill(existingBill);
        return billTicketRepository.save(existingBillTicket);
    }

    @Override
    public String deleteBillTicket(int id) throws Exception {
        BillTicket existingBillTicket = billTicketRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found bill ticket"));
        billTicketRepository.delete(existingBillTicket);
        return "Delete Successfully";
    }
}
