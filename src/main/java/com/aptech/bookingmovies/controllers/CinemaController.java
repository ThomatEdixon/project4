package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.CinemaDTO;
import com.aptech.bookingmovies.models.Cinema;
import com.aptech.bookingmovies.models.User;
import com.aptech.bookingmovies.services.CinemaService;
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
@RequestMapping("${api.prefix}/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/findByCinemaName")
    public ResponseEntity<?>findCinemaByName(@RequestParam String name) throws Exception{
        List<Cinema> cinemas = cinemaService.getByName(name);
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("/findCinemaId")
    public ResponseEntity<?> findCinemaId(@RequestParam int id) throws Exception{
        Cinema cinema = cinemaService.findById(id);
        return ResponseEntity.ok(cinema);
    }
    @GetMapping("")
    public ResponseEntity<?> listCinema(){
        return ResponseEntity.ok(cinemaService.getAll());
    }

    @PostMapping("/createCinema")
    public ResponseEntity<?> createCinema(@Valid @RequestBody CinemaDTO cinemaDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Cinema newCinema=cinemaService.createCinema(cinemaDTO);
            return ResponseEntity.ok(newCinema);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateCinema/{id}")
    public ResponseEntity<?> updateCinema(@Valid @RequestBody CinemaDTO cinemaDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Cinema newCinema=cinemaService.updateCinema(id,cinemaDTO);
            return ResponseEntity.ok(newCinema);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteCinema/{id}")
    public ResponseEntity<?> deleteCinema(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(cinemaService.deleteById(id));
    }
}
