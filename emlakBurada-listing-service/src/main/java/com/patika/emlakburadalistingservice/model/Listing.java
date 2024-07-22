package com.patika.emlakburadalistingservice.model;

import com.patika.emlakburadalistingservice.model.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "listing")

public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "user_id")
    private Long  userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ListingStatus listingStatus;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    @Column(name = "price")
    private Double price;
    @Column(name = "details", columnDefinition =  "TEXT")
    private String details;
    @Column(name = "address")
    private String address;
    @Column(name = "summary", columnDefinition =  "TEXT")
    private String summary;
    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Photo photo;
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "size")
    private Integer size;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "age")
    private Integer age;
    @Column(name = "deposit")
    private Double deposit;

    public Listing(String title, Long userId, ListingStatus listingStatus, LocalDateTime createDate, LocalDateTime expirationDate, Double price, String details, String address, String summary, Integer roomNumber, Integer size, Integer floor, Integer age, Double deposit) {
        this.title = title;
        this.userId = userId;
        this.listingStatus = listingStatus;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.price = price;
        this.details = details;
        this.address = address;
        this.summary = summary;
        this.roomNumber = roomNumber;
        this.size = size;
        this.floor = floor;
        this.age = age;
        this.deposit = deposit;
    }

}
