package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.CreateOrderRequest;
import com.example.ordermanagement.dto.UpdateStatusRequest;
import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.Product;
import com.example.ordermanagement.model.User;
import com.example.ordermanagement.repository.OrderRepository;
import com.example.ordermanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request, @AuthenticationPrincipal User user) {
        Order order = new Order();
        order.setUser(user);

        Set<Product> products = new HashSet<>(productRepository.findAllById(request.getProductIds()));
        order.setProducts(products);

        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/my-history")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!")); // Слід замінити на кастомний Exception

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);

        return ResponseEntity.ok(updatedOrder);
    }
}