package com.example.ottoservice.controller;

import com.example.ottoservice.model.Category;
import com.example.ottoservice.service.ServiceCategoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    @Autowired
    ServiceCategoryImpl serviceCategory;

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return serviceCategory.getAll();
    }

    @GetMapping("/admin_categories")
    public ResponseEntity<Collection<Category>> getCategories(@RequestParam Optional<Integer> _end,
                                                              @RequestParam Optional<String> _order,
                                                              @RequestParam Optional<String> _sort,
                                                              @RequestParam Optional<Integer> _start,
                                                              @RequestParam Optional<List<Integer>> id) {

        Collection<Category> allCategories = serviceCategory.getAll();
        Collection<Category> result = id.isPresent() ? allCategories.stream().
                filter(category -> id.get().contains(category.getCategory_id())).collect(Collectors.toList()) :
                allCategories;

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Expose-Headers", "X-Total-Count");
        responseHeaders.set("X-Total-Count", String.valueOf(result.size()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(result);
    }

    @GetMapping("/admin_categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category result = serviceCategory.getEntityById(id);
        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping("/admin_categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category result = serviceCategory.createEntity(category);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/admin_categories/{id}")
    public ResponseEntity<Category> editCategory(@RequestBody Category category) {
        Category result = serviceCategory.createEntity(category);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/admin_categories/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
                Boolean isDeleted = serviceCategory.deleteEntityByID(id);
            return ResponseEntity.ok()
                    .body(isDeleted);
    }
}
