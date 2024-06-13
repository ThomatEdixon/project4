package com.aptech.bookingmovies.services;

import com.aptech.bookingmovies.dtos.MovieDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.BillTicket;
import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.models.MovieType;
import com.aptech.bookingmovies.models.Rate;
import com.aptech.bookingmovies.repositories.MovieRepository;
import com.aptech.bookingmovies.repositories.MovieTypeRepository;
import com.aptech.bookingmovies.repositories.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService{
    private final MovieRepository movieRepository;
    private final RateRepository rateRepository;
    private final MovieTypeRepository movieTypeRepository;

    @Override
    public Movie createMovie(MovieDTO movieDTO) throws Exception {
        Rate rate = rateRepository.findById(movieDTO.getRateId())
                .orElseThrow(()->new DataNotFoundException("Can not found rate"));
        MovieType movieType = movieTypeRepository.findById(movieDTO.getMovieTypeId())
                .orElseThrow(()->new DataNotFoundException("Can not found movie type"));
        Movie newMovie = Movie.builder()
                .movieDuration(movieDTO.getMovieDuration())
                .endTime(movieDTO.getEndTime())
                .premiereDate(movieDTO.getPremiereDate())
                .description(movieDTO.getDescription())
                .director(movieDTO.getDirector())
                .image(movieDTO.getImage())
                .heroImage(movieDTO.getHeroImage())
                .language(movieDTO.getLanguage())
                .name(movieDTO.getName())
                .trailer(movieDTO.getTrailer())
                .movieType(movieType)
                .rate(rate)
                .isActive(true)
                .build();
        return movieRepository.save(newMovie);
    }

    @Override
    public List<Movie> findByName(String name) throws Exception {
        List<Movie> movies = this.findAll();
        List<Movie> result = new ArrayList<>();
        for(Movie m : movies){
            if(m.getName().contains(name)){
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public Movie updateMovie(int id, MovieDTO movieDTO) throws Exception {
        Rate rate = rateRepository.findById(movieDTO.getRateId()).orElseThrow();
        MovieType movieType = movieTypeRepository.findById(movieDTO.getMovieTypeId())
                .orElseThrow(()->new DataNotFoundException("Can not found movie type"));
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new DateTimeException("Can not found movie"));
        existingMovie.setMovieDuration(movieDTO.getMovieDuration());
        existingMovie.setEndTime(movieDTO.getEndTime());
        existingMovie.setPremiereDate(movieDTO.getPremiereDate());
        existingMovie.setDescription(movieDTO.getDescription());
        existingMovie.setDirector(movieDTO.getDirector());
        existingMovie.setImage(movieDTO.getImage());
        existingMovie.setHeroImage(movieDTO.getHeroImage());
        existingMovie.setLanguage(movieDTO.getLanguage());
        existingMovie.setName(movieDTO.getName());
        existingMovie.setTrailer(movieDTO.getTrailer());
        existingMovie.setMovieType(movieType);
        existingMovie.setRate(rate);
        return movieRepository.save(existingMovie);
    }

    @Override
    public String deleteMovie(int id) throws Exception {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new DateTimeException("Can not found movie"));
        movieRepository.delete(existingMovie);
        return "Delete Successfully";
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = movieRepository.findAll();
        List<Movie> result = new ArrayList<>();
        for(Movie m :movies){
            if(m.isActive()){
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public Movie findById(int id) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new DateTimeException("Can not found movie"));
        return existingMovie;
    }
}
