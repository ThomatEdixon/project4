package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "role_name")
    private String roleName;
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String CINEMA = "CINEMA";
}
