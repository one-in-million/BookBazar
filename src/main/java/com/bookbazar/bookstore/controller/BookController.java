package com.bookbazar.bookstore.controller;

import com.bookbazar.bookstore.entity.Book;
import com.bookbazar.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Requirements Covered:
     * 1. Browse all books
     * 2. Search by keyword (Title, Author, Category)
     * 3. Filter by Max Price
     * 4. View Stock (Included in every book object)
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Book>> searchBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        // 1. If user wants to browse by price range
        if (maxPrice != null) {
            return ResponseEntity.ok(bookRepository.findByPriceLessThanEqual(maxPrice, pageable));
        }

        // 2. If user provides a keyword (Searches Title, Category, and Author)
        if (keyword != null && !keyword.isEmpty()) {
            return ResponseEntity.ok(bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                    keyword, keyword, keyword, pageable));
        }

        // 3. Default: Browse everything (Shows all books with prices, categories, authors, and stock)
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }
}