package com.example.demo.web.rest;

import com.example.demo.dto.response.CategoryResponse;
import com.example.demo.dto.response.ProductResponse;
import com.example.demo.dto.resquest.CategoryRequest;
import com.example.demo.sevice.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @PostMapping
//    public CategoryResponse createCategoryId(CategoryRequest request){
//        return categoryService.createCategoryId(request);
//    }
    @GetMapping("/{id}/categories")
    public CategoryResponse findById(@PathVariable Long id){
    return categoryService.findById(id);
    }

    @GetMapping("/{id}/products")
    public List<ProductResponse> findProductResponseByCategoryId(@PathVariable Long id){
        return categoryService.findProductResponseByCategoryId(id);
    }
    @PostMapping("/create")
    public CategoryResponse create(@RequestBody CategoryRequest categoryRequest){
        return categoryService.create(categoryRequest);
    }

    @PutMapping("/{id}/update")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }

}
