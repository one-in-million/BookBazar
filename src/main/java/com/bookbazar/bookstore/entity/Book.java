package com.bookbazar.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String category;
    private Double price;
    private Integer stock;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
