package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.RoomDTO;
import com.aptech.bookingmovies.dtos.SeatDTO;
import com.aptech.bookingmovies.models.Room;
import com.aptech.bookingmovies.models.Seat;
import com.aptech.bookingmovies.services.SeatService;
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
@RequestMapping("${api.prefix}/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    @GetMapping("")
    public ResponseEntity<?> listSeat(){
        return ResponseEntity.ok(seatService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSeat(@Valid @RequestBody SeatDTO seatDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Seat neaSeat = seatService.create(seatDTO);
            return ResponseEntity.ok(neaSeat);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody SeatDTO seatDTO, @PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Seat neaSeat = seatService.update(id,seatDTO);
            return ResponseEntity.ok(neaSeat);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(seatService.delete(id));
    }
}
