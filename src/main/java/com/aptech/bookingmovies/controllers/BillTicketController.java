package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.BillTicketDTO;
import com.aptech.bookingmovies.models.BillTicket;
import com.aptech.bookingmovies.services.billticket.BillTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/bill_ticket")
@RequiredArgsConstructor
public class BillTicketController {
    private final BillTicketService billTicketService;
    @GetMapping("/findByCustomerPhoneNumber")
    public ResponseEntity<?> listBill(@RequestParam String phoneNumber) throws Exception{
        List<BillTicket> bills = billTicketService.findBillTicketByCustomerPhoneNumber(phoneNumber);
        return ResponseEntity.ok(bills);
    }
    @GetMapping("/findBillTicketId")
    public ResponseEntity<?> findBillTicketId(@RequestParam int id) throws Exception{
        BillTicket billTicket = billTicketService.findById(id);
        return ResponseEntity.ok(billTicket);
    }

    @PostMapping("/createBillTicket")
    public ResponseEntity<?> createBillTicket(@Valid @RequestBody BillTicketDTO billTicketDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            BillTicket billTicket= billTicketService.createBillTicket(billTicketDTO);
            return ResponseEntity.ok(billTicket);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateBillTicket/{id}")
    public ResponseEntity<?> updateBillTicket(@Valid @RequestBody BillTicketDTO billTicketDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            BillTicket billTicket= billTicketService.updateBillTicket(id,billTicketDTO);
            return ResponseEntity.ok(billTicket);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteBill/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(billTicketService.deleteBillTicket(id));
    }
}
