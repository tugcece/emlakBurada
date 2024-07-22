package com.patika.emlakburadaauthenticationservice.repository;

import com.patika.emlakburadaauthenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByemail(String email);
}