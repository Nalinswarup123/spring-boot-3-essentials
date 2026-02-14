package com.example.roomwebapp;

import com.example.roomwebapp.data.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoomWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomWebAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoomRepository repository) {
        return args -> {
            repository.findAll().forEach(System.out::println);
        };
    }

}
