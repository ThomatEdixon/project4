package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.CinemaDTO;
import com.aptech.bookingmovies.dtos.MovieDTO;
import com.aptech.bookingmovies.models.Cinema;
import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.services.MovieService;
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
@RequestMapping("${api.prefix}/movie")
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/findByMovieName")
    public ResponseEntity<?> findMovieByName(@RequestParam String name) throws Exception{
        List<Movie> cinemas = movieService.findByName(name);
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("")
    public ResponseEntity<?> listMovie(){
        return ResponseEntity.ok(movieService.findAll());
    }

    @PostMapping("/createMovie")
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieDTO movieDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Movie newMovie = movieService.createMovie(movieDTO);
            return ResponseEntity.ok(newMovie);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateMovie/{id}")
    public ResponseEntity<?> updateMovie(@Valid @RequestBody MovieDTO movieDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Movie newMovie = movieService.updateMovie(id,movieDTO);
            return ResponseEntity.ok(newMovie);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id" )int id) throws Exception{
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }
}
