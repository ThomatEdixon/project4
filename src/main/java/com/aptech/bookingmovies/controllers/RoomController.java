package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.RoomDTO;
import com.aptech.bookingmovies.models.Room;
import com.aptech.bookingmovies.services.RoomService;
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
@RequestMapping("${api.prefix}/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @GetMapping("/findByCinemaName")
    public ResponseEntity<?> findRoomByName(@RequestParam  String name) throws Exception{
        Room room = roomService.findByRoomName(name);
        return ResponseEntity.ok(room);
    }

    @GetMapping("")
    public ResponseEntity<?> listRoom(){
        return ResponseEntity.ok(roomService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDTO roomDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Room newRoom = roomService.createRoom(roomDTO);
            return ResponseEntity.ok(newRoom);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody RoomDTO roomDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Room newRoom = roomService.update(id,roomDTO);
            return ResponseEntity.ok(newRoom);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(roomService.delete(id));
    }
}
