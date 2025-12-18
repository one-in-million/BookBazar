package com.bookbazar.bookstore.config;

import com.bookbazar.bookstore.entity.*;
import com.bookbazar.bookstore.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepo, UserRepository userRepo) {
        return args -> {
            if (userRepo.count() == 0) {
                // 1. Adding 3 Users
                userRepo.save(new User(null, "John Doe", "john.doe@example.com", LocalDateTime.now()));
                userRepo.save(new User(null, "Alice Smith", "alice.s@example.com", LocalDateTime.now()));
                userRepo.save(new User(null, "Bob Wilson", "bob.w@example.com", LocalDateTime.now()));

                // 2. Adding more Books (to test pagination and categories)
                bookRepo.save(new Book(null, "Java Programming", "John Smith", "Education", 1200.0, 10, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Spring Boot Pro", "Craig Walls", "Programming", 950.0, 15, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Mastering MySQL", "Database Expert", "Education", 800.0, 20, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Clean Code", "Robert Martin", "Programming", 1100.0, 5, LocalDateTime.now()));
                bookRepo.save(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 500.0, 20, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Design Patterns", "Gang of Four", "Tech", 1500.0, 12, LocalDateTime.now()));
                bookRepo.save(new Book(null, "React Native 101", "Mobile Dev", "Programming", 700.0, 25, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Python for Data Science", "Data Wiz", "Education", 900.0, 30, LocalDateTime.now()));
                bookRepo.save(new Book(null, "AWS Essentials", "Cloud Team", "Tech", 1000.0, 15, LocalDateTime.now()));
                bookRepo.save(new Book(null, "Algorithms", "Thomas Cormen", "Education", 2000.0, 10, LocalDateTime.now()));

                System.out.println("--- Expanded Sample Data Initialized ---");
            }
        };
    }
}