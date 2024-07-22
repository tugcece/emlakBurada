package com.patika.emlakburadalistingservice.repository;

import com.patika.emlakburadalistingservice.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> , JpaSpecificationExecutor<Listing> {
   @Override
   Optional<Listing> findById(Long id);
}
