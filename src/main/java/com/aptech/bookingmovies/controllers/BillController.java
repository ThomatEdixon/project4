package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.BillDTO;
import com.aptech.bookingmovies.dtos.CinemaDTO;
import com.aptech.bookingmovies.models.Bill;
import com.aptech.bookingmovies.models.Cinema;
import com.aptech.bookingmovies.services.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    @GetMapping("/findBillByTradingCode")
    public ResponseEntity<?> findBillByTradingCode(@RequestParam String tradingCode) throws Exception{
        Bill bill = billService.findBillByTradingCode(tradingCode);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/findByCustomerPhoneNumber")
    public ResponseEntity<?> listBill(@RequestParam String phoneNumber) throws Exception{
        List<Bill> bills = billService.findBillByCustomerPhoneNumber(phoneNumber);
        return ResponseEntity.ok(bills);
    }

    @PostMapping("/createBill")
    public ResponseEntity<?> createBill(@Valid @RequestBody BillDTO billDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Bill newBill = billService.createBill(billDTO);
            return ResponseEntity.ok(newBill);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateBill/{id}")
    public ResponseEntity<?> updateBill(@Valid @RequestBody BillDTO billDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Bill newBill = billService.updateBill(id,billDTO);
            return ResponseEntity.ok(newBill);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteBill/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(billService.deleteBill(id));
    }
}
