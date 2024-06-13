package com.aptech.bookingmovies.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "total_money")
    private double totalMoney;
    @Column(name = "trading_code")
    private String tradingCode;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "name")
    private String name;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "bill_status")
    private String billStatus;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;
    @ManyToOne
    @JoinColumn
    private Promotion promotion;
}
