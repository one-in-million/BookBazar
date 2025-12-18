package com.bookbazar.bookstore.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long bookId;
    private Integer quantity;
}