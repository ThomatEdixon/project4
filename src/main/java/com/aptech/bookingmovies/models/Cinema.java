package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Data
@Document(indexName = "cinema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;
    @Column(name = "name_of_cinema")
    private String nameOfCinema;
    @Column(name="is_active")
    private boolean isActive;
}
