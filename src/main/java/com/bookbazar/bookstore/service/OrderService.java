package com.bookbazar.bookstore.service;

import com.bookbazar.bookstore.dto.OrderItemRequest;
import com.bookbazar.bookstore.entity.*;
import com.bookbazar.bookstore.exception.InsufficientStockException;
import com.bookbazar.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Requirement 1: Place Order with Stock Check, Tax, and Discount
     */
    @Transactional
    public Order placeOrder(Long userId, List<OrderItemRequest> itemRequests) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PLACED);

        double subtotal = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : itemRequests) {
            Book book = bookRepository.findById(req.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found ID: " + req.getBookId()));

            if (book.getStock() < req.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for book: " + book.getTitle());
            }

            book.setStock(book.getStock() - req.getQuantity());
            bookRepository.save(book);

            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setOrder(order);
            item.setQuantity(req.getQuantity());
            item.setPriceAtPurchase(book.getPrice());
            orderItems.add(item);

            subtotal += (book.getPrice() * req.getQuantity());
        }

        double gst = subtotal * 0.05;
        double finalAmount = subtotal + gst;

        if (finalAmount > 1000) {
            finalAmount = finalAmount * 0.90; // Apply 10% Discount
        }

        order.setTotalAmount(finalAmount);
        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    /**
     * Requirement 2: Cancel Order & Restore Stock
     * Endpoint: PUT /orders/{id}/cancel
     */
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled.");
        }

        // Logic: Restore book stock for each item in the order
        for (OrderItem item : order.getItems()) {
            Book book = item.getBook();
            book.setStock(book.getStock() + item.getQuantity());
            bookRepository.save(book);
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    /**
     * Requirement 3: Get Paginated Orders by User
     * Endpoint: GET /orders/user/{userId}
     */
    public Page<Order> getOrderHistory(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        return orderRepository.findByUserId(userId, pageable);
    }
}