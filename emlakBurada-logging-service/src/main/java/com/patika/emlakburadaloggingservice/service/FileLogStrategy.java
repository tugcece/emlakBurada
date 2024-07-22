package com.patika.emlakburadaloggingservice.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileLogStrategy implements LogStrategy {

    private static final String FILE_PATH = "logs/application.log";

    @Override
    public void log(String message) {
        try {
            // Ensure the logs directory exists
            Path logDir = Paths.get("logs");
            if (!Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            try (FileWriter fw = new FileWriter(FILE_PATH, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(LocalDateTime.now() + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
