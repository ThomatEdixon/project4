package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.CinemaDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Cinema;
import com.aptech.bookingmovies.repositories.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CinemaService implements ICinemaService{
    private final CinemaRepository cinemaRepository;
    @Override
    public Cinema createCinema(CinemaDTO cinemaDTO) throws Exception {
        Cinema newCinema = Cinema.builder()
                .address(cinemaDTO.getAddress())
                .description(cinemaDTO.getDescription())
                .code(generateCinemaCode())
                .nameOfCinema(cinemaDTO.getNameOfCinema())
                .isActive(true)
                .build();
        return cinemaRepository.save(newCinema);
    }

    @Override
    public Cinema updateCinema(int id, CinemaDTO cinemaDTO) throws Exception {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found cinema"));
        cinema.setNameOfCinema(cinemaDTO.getNameOfCinema());
        cinema.setAddress(cinemaDTO.getAddress());
        cinema.setDescription(cinemaDTO.getDescription());
        return cinemaRepository.save(cinema);
    }

    @Override
    public List<Cinema> getByName(String name) throws Exception{
        List<Cinema> cinemas = this.getAll();
        List<Cinema> cinema = new ArrayList<>();
        if(cinemas.isEmpty()){
            throw new DataNotFoundException("Can not found Ciname");
        }else {
            for(Cinema c:cinemas){
                if(c.getNameOfCinema().contains(name)&& c.isActive()){
                    cinema.add(c);
                }
            }
        }

        return cinema;
    }

    @Override
    public String deleteById(int id) throws Exception{
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found cinema"));
        cinema.setActive(false);
        cinemaRepository.save(cinema);
        return "Delete Successfully";
    }

    @Override
    public List<Cinema> getAll() {
        Iterable<Cinema> cinemas = cinemaRepository.findAll();
        List<Cinema> results = new ArrayList<>();
        for(Cinema c : cinemas){
            if(c.isActive()){
                results.add(c);
            }
        }
        return results;
    }

    public String generateCinemaCode(){
        String cinemaCode="C";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        cinemaCode +=sb.toString();
        return cinemaCode;
    }
}
