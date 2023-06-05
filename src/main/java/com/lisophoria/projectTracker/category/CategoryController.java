package com.lisophoria.projectTracker.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping("/by-user/{userId}")
    public List<Category> getCategoriesByUserId(@PathVariable("userId") Integer userId) {
        return categoryRepository.getByUserId(userId);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategoryById(@PathVariable("categoryId") Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @PostMapping
    public Category save(
        @RequestBody Category request
    ) {
        return categoryRepository.save(request);
    }
}
