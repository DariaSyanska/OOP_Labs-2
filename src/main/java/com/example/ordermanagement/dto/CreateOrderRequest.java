package com.example.ordermanagement.dto;

import lombok.Data;
import java.util.Set;

@Data
public class CreateOrderRequest {
    private Set<Long> productIds;
}