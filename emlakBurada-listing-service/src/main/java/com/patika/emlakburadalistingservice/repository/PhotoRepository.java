package com.patika.emlakburadalistingservice.repository;

import com.patika.emlakburadalistingservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByListingId(Long id);
    void deleteByListingId(Long listingId);
}
