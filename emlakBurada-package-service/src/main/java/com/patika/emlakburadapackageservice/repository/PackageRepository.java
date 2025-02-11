package com.patika.emlakburadapackageservice.repository;

import com.patika.emlakburadapackageservice.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
}
