package com.patika.emlakburadaloggingservice.repository;

import com.patika.emlakburadaloggingservice.model.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogMessage, Long> {
}
