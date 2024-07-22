package com.patika.emlakburadapackageservice.model;

import com.patika.emlakburadapackageservice.model.enums.PackageTypes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private PackageTypes packageName;
    @Column(name = "validity_days")
    private int validityPeriod;
    @Column (name =  "price")
    private Double price;
    @Column (name = "listing_rights")
    private int listingRights;
}
