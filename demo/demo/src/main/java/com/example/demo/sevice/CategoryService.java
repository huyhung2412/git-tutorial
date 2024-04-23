package com.example.demo.sevice;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.resquest.CategoryRequest;
import com.example.demo.entity.Category;

import java.util.List;

public interface CategoryService extends BaseCrudService<CategoryRequest, Category, Long, CategoryResponse> {

//    CategoryResponse createCategoryId(CategoryRequest request);

        List<ProductResponse>  findProductResponseByCategoryId(Long id);

        CategoryResponse toResponse(Category category);
}
