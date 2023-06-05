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
}
