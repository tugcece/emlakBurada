package com.patika.emlakburadalistingstatusservice.repository;

import com.patika.emlakburadalistingstatusservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
    @Override
    Optional<Listing> findById(Integer id);
}

