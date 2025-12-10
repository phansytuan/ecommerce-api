package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ApiResponse;
import com.ecommerce.api.dto.ProductRequest;
import com.ecommerce.api.dto.ProductResponse;
import com.ecommerce.api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.createProduct(request);
        ApiResponse response = new ApiResponse(true, "Product created successfully", product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        ApiResponse response = new ApiResponse(true, "Product retrieved successfully", product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(@RequestParam(required = false) Boolean active) {
        List<ProductResponse> products;
        if (active != null && active) {
            products = productService.getActiveProducts();
        } else {
            products = productService.getAllProducts();
        }
        ApiResponse response = new ApiResponse(true, "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        List<ProductResponse> products = productService.getProductsByCategory(category);
        ApiResponse response = new ApiResponse(true, "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProducts(@RequestParam String keyword) {
        List<ProductResponse> products = productService.searchProducts(keyword);
        ApiResponse response = new ApiResponse(true, "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        ApiResponse response = new ApiResponse(true, "Product updated successfully", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse response = new ApiResponse(true, "Product deleted successfully");
        return ResponseEntity.ok(response);
    }
}
