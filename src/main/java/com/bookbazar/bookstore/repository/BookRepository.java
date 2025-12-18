package com.bookbazar.bookstore.repository;

import com.bookbazar.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Requirement 4.4 & Browse: Search by Title, Category, OR Author
    Page<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title, String category, String author, Pageable pageable);

    // Requirement: Browse by Price range
    Page<Book> findByPriceLessThanEqual(Double price, Pageable pageable);
}