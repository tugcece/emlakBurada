package com.patika.emlakburadalistingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String name;
    private String type;

    @OneToOne
    @JoinColumn(name = "listing_id", unique = true)
    private Listing listing;

    public Photo(String name, String type, byte[] data, Listing listing) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.listing = listing;
    }
}
