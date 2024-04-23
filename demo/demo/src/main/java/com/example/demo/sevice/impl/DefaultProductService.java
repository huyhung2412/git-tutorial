package com.example.demo.sevice.impl;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.resquest.ProductRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.sevice.AbstractCrudService;
import com.example.demo.sevice.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService
        extends AbstractCrudService<ProductRequest, ProductRepository, Product, Long, ProductResponse>
        implements ProductService {

    private final CategoryRepository categoryRepository;

    public DefaultProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        super(repository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Product createAndSave(ProductRequest request) {
        Category categoryRef = categoryRepository.getReferenceById(request.getCategoryId());
        try {
            categoryRef.getId();
        }catch (EntityNotFoundException e ){
            System.err.println(e.getMessage());
            throw new BadRequestException(e.getMessage(), e);
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDesc(request.getDesc());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setCategory(categoryRef);

        return repository.save(product);
    }

    @Override
    public ProductResponse toResponse(Product saveProduct) {
        ProductResponse response = ProductResponse.builder()
                .id(saveProduct.getId())
                .name(saveProduct.getName())
                .desc(saveProduct.getDesc())
                .quantity(saveProduct.getQuantity())
                .price(saveProduct.getPrice()).build();

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(saveProduct.getId())
                .name(saveProduct.getName()).build();

        response.setCategory(categoryResponse);
        return response;
    }


    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product whith id" + "í not found"));
        if (product!=null){
            product.setName(request.getName());
            product.setDesc(request.getDesc());
            product.setQuantity(request.getQuantity());
            product.setPrice(request.getPrice());
            repository.save(product);
        }
        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .desc(product.getDesc())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
        return response;
    }

    @Override
    protected Product updateAndSave(Long id, ProductRequest request) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product whith id" + "is not found"));
        product.setName(request.getName());
        product.setDesc(request.getDesc());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        return repository.save(product);
    }

}
