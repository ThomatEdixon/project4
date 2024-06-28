package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.dtos.MovieDTO;
import com.aptech.bookingmovies.models.Movie;
import com.aptech.bookingmovies.models.MovieType;
import com.aptech.bookingmovies.services.movie.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/movie")
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieService movieService;
    @GetMapping("/findByMovieName")
    public ResponseEntity<?> findMovieByName(@RequestParam String name) throws Exception{
        List<Movie> cinemas = movieService.findByName(name);
        return ResponseEntity.ok(cinemas);
    }
    @GetMapping("/findMovieTypeByMovieId")
    public ResponseEntity<?> findMovieTypeByMovieId(@RequestParam int id) throws Exception{
        List<MovieType> movieTypes = movieService.findMovieTypeById(id);
        return ResponseEntity.ok(movieTypes);
    }
    @GetMapping("/findMovieByMovieType")
    public ResponseEntity<?> findMovieByMovieType(@RequestParam int id) throws Exception{
        List<Movie> movies = movieService.findMoviesByType(id);
        return ResponseEntity.ok(movies);
    }
    @PostMapping("/addMovieTypeToMovie")
    public ResponseEntity<?> addMovieTypeToMovie(@RequestParam int movieId, @RequestParam int movieTypeId) throws Exception{
        boolean result = movieService.addMovieTypeToMovie(movieId, movieTypeId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/findMovieId")
    public ResponseEntity<?> findMovieId(@RequestParam int id) throws Exception{
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }
    @GetMapping("")
    public ResponseEntity<?> listMovie(){
        return ResponseEntity.ok(movieService.findAll());
    }

    @PostMapping(value = "/createMovie",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMovie( @Valid @ModelAttribute MovieDTO movieDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            MultipartFile file = movieDTO.getFile();
            if(file != null) {
                if(file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
            }
            String filename = storeFile(file);
            movieDTO.setImage(filename);
            Movie newMovie = movieService.createMovie(movieDTO);
            return ResponseEntity.ok(newMovie);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/images")
    public ResponseEntity<?> viewImage(@RequestParam String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                logger.info(imageName + " not found");
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving image: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping(value ="/updateMovie/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateMovie(@Valid @ModelAttribute MovieDTO movieDTO,@PathVariable("id") int id, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            MultipartFile file = movieDTO.getFile();
            if(file != null) {
                if(file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
            }
            String filename = storeFile(file);
            movieDTO.setImage(filename);
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
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
