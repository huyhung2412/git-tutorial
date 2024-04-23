package com.example.demo.sevice;

import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.resquest.ProductRequest;
import com.example.demo.entity.Product;

public interface ProductService {
    ProductResponse create (ProductRequest request);

    ProductResponse findById(Long id);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    ProductResponse toResponse(Product product);
}
