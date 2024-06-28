package com.aptech.bookingmovies.repositories;

import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.models.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
    @Query("SELECT COUNT(m) > 0 FROM Movie m JOIN m.movieTypes mt WHERE m.id = :movieId AND mt.id = :movieTypeId")
    boolean existsByMovieIdAndMovieTypeId(@Param("movieId") int movieId, @Param("movieTypeId") int movieTypeId);
    @Query("SELECT m FROM MovieType mt JOIN mt.movies m WHERE mt.id = :movieTypeId")
    List<Movie> findMoviesByType(@Param("movieTypeId") int movieTypeId);
    @Query("SELECT mt FROM Movie m JOIN m.movieTypes mt WHERE m.id = :movieId")
    List<MovieType> findMovieTypesByMovieId(@Param("movieId") int movieId);

}
