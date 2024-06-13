package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Data
@Document(indexName =  "movie_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movie_type_name")
    private String movieTypeName;
    @Column(name = "is_active")
    private boolean isActive;
}
