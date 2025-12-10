package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ApiResponse;
import com.ecommerce.api.dto.OrderRequest;
import com.ecommerce.api.dto.OrderResponse;
import com.ecommerce.api.entity.Order;
import com.ecommerce.api.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> createOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        OrderResponse order = orderService.createOrder(username, request);
        ApiResponse response = new ApiResponse(true, "Order created successfully", order);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        ApiResponse response = new ApiResponse(true, "Order retrieved successfully", order);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{orderNumber}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> getOrderByNumber(@PathVariable String orderNumber) {
        OrderResponse order = orderService.getOrderByNumber(orderNumber);
        ApiResponse response = new ApiResponse(true, "Order retrieved successfully", order);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        ApiResponse response = new ApiResponse(true, "Orders retrieved successfully", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        List<OrderResponse> orders = orderService.getUserOrders(username);
        ApiResponse response = new ApiResponse(true, "Orders retrieved successfully", orders);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        OrderResponse order = orderService.updateOrderStatus(id, status);
        ApiResponse response = new ApiResponse(true, "Order status updated successfully", order);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        ApiResponse response = new ApiResponse(true, "Order cancelled successfully");
        return ResponseEntity.ok(response);
    }
}
