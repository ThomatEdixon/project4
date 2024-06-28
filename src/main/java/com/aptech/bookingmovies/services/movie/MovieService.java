package com.aptech.bookingmovies.services.movie;

import com.aptech.bookingmovies.dtos.MovieDTO;
import com.aptech.bookingmovies.exceptions.DataNotFoundException;
import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.models.MovieType;
import com.aptech.bookingmovies.models.Rate;
import com.aptech.bookingmovies.repositories.MovieRepository;
import com.aptech.bookingmovies.repositories.MovieTypeRepository;
import com.aptech.bookingmovies.repositories.RateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService {
    private final MovieRepository movieRepository;
    private final RateRepository rateRepository;
    private final MovieTypeRepository movieTypeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Movie createMovie(MovieDTO movieDTO) throws Exception {
        Rate rate = rateRepository.findById(movieDTO.getRateId())
                .orElseThrow(()->new DataNotFoundException("Can not found rate"));
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
        existingMovie.setRate(rate);
        return movieRepository.save(existingMovie);
    }

    @Override
    public String deleteMovie(int id) throws Exception {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found movie"));
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
    public Movie findById(int id) throws Exception{
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Can not found movie"));
        return existingMovie;
    }

    @Override
    @Transactional
    public Boolean addMovieTypeToMovie(int movieId, int movieTypeId) throws Exception{
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new DataNotFoundException("Can not found movie"));
        MovieType movieType = movieTypeRepository.findById(movieTypeId).orElseThrow(() -> new DataNotFoundException("MCan not found movie type"));

        if (movieRepository.existsByMovieIdAndMovieTypeId(movieId, movieTypeId)) {
            System.out.println("MovieType đã được liên kết với Movie này");
            return false;
        }

        System.out.println("Trước khi thêm: " + movie.getMovieTypes());
        movie.getMovieTypes().add(movieType);
        System.out.println("Sau khi thêm: " + movie.getMovieTypes());

        // Save movie to ensure the join table is updated
        movieRepository.save(movie);

        return true;
    }

    @Override
    @Transactional
    public List<Movie> findMoviesByType(int movieTypeId) throws Exception {
        MovieType movieType = movieTypeRepository.findById(movieTypeId)
                .orElseThrow(() -> new DataNotFoundException("Can not found movie type"));
        List<Movie> movies = movieRepository.findMoviesByType(movieTypeId);
        System.out.println("Movies found for MovieType: " + movies);
        return movies;
    }

    @Override
    public List<MovieType> findMovieTypeById(int id) throws Exception {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not found movie"));
        List<MovieType> movieTypes = movieRepository.findMovieTypesByMovieId(id);
        return movieTypes;
    }
}
