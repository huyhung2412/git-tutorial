package com.example.demo.sevice.impl;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.resquest.CategoryRequest;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.sevice.AbstractCrudService;
import com.example.demo.sevice.CategoryService;
import com.example.demo.sevice.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCategoryService
        extends AbstractCrudService<CategoryRequest, CategoryRepository, Category, Long, CategoryResponse>
        implements CategoryService {

    private final ProductService productService;

    public DefaultCategoryService(CategoryRepository repository, ProductService productService) {
        super(repository);
        this.productService = productService;
    }

    @Override
    public List<ProductResponse> findProductResponseByCategoryId(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category with id " + id + "is not found"));
        List<Product> products = category.getProducts();

        List<ProductResponse> res = products.stream().map(productService::toResponse).toList();
        return res;
    }

    @Override
    protected Category createAndSave(CategoryRequest request) {
        return repository.save(Category.builder()
                .name(request.getName())
                .build());
    }

    @Override
    protected Category updateAndSave(Long id, CategoryRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id %s is not found".formatted(id)));
        category.setName(request.getName());

        return repository.save(category);

    }


    @Override
    public CategoryResponse toResponse(Category saveCategory) {
        CategoryResponse response = CategoryResponse.builder().name(saveCategory.getName())
                .id(saveCategory.getId()).build();

        return response;
    }


}
