package com.bookbazar.bookstore.controller;

import com.bookbazar.bookstore.entity.Order;
import com.bookbazar.bookstore.dto.OrderItemRequest;
import com.bookbazar.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Requirement: POST /orders
     * Places a new order and calculates GST/Discounts.
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> payload) {
        Long userId = Long.valueOf(payload.get("userId").toString());

        @SuppressWarnings("unchecked")
        List<OrderItemRequest> items = ((List<Map<String, Object>>) payload.get("items"))
                .stream()
                .map(item -> {
                    OrderItemRequest req = new OrderItemRequest();
                    req.setBookId(Long.valueOf(item.get("bookId").toString()));
                    req.setQuantity((Integer) item.get("quantity"));
                    return req;
                }).toList();

        return ResponseEntity.ok(orderService.placeOrder(userId, items));
    }

    /**
     * Requirement: PUT /orders/{id}/cancel
     * Cancels the order and restores book stock.
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    /**
     * Requirement: GET /orders/user/{userId}
     * Returns paginated order history for a user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Order>> getOrderHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(orderService.getOrderHistory(userId, PageRequest.of(page, size)));
    }
}