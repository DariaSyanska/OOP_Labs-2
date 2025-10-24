package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.Order;
import com.example.ordermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Finds all orders placed by a specific user.
     * @param user the user whose orders to find
     * @return a list of orders for the given user
     */
    List<Order> findByUser(User user);
}