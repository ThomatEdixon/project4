package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Data
@Table(name =  "rank_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = " point")
    private int point;
    @Column(name="description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;
}
