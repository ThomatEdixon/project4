package com.aptech.bookingmovies.services.movie;

import com.aptech.bookingmovies.dtos.MovieDTO;
import com.aptech.bookingmovies.models.Movie;

import java.util.List;

public interface IMovieService {
    Movie createMovie(MovieDTO movieDTO) throws Exception;
    List<Movie> findByName(String name) throws Exception;
    Movie updateMovie(int id, MovieDTO movieDTO)throws Exception;
    String deleteMovie(int id) throws Exception;
    List<Movie> findAll();
    Movie findById(int id);
}
