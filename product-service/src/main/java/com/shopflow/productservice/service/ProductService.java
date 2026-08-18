package com.shopflow.productservice.service;

import com.shopflow.productservice.dto.CreateProductRequest;
import com.shopflow.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
}