package com.lisophoria.projectTracker.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskRepository taskRepository;

    @GetMapping("/by-category/{categoryId}")
    public List<Task> getCategoriesByUserId(@PathVariable("categoryId") Integer categoryId) {
        return taskRepository.getByCategoryId(categoryId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteCategoryById(@PathVariable("taskId") Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    @PostMapping
    public Task save(
        @RequestBody Task request
    ) {
        return taskRepository.save(request);
    }
}
