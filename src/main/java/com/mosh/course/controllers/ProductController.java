package com.mosh.course.controllers;

import com.mosh.course.dtos.ProductDto;
import com.mosh.course.dtos.UserDto;
import com.mosh.course.mappers.ProductMapper;
import com.mosh.course.models.Product;
import com.mosh.course.repositories.CategoryRepository;
import com.mosh.course.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, defaultValue = "", name = "categoryId") Short categoryId
    ) {
        List<Product> products;
        if(categoryRepository.findAllCategoryIds().contains(categoryId)) products = productRepository.findByCategoryId(categoryId);
        else products = productRepository.findAll();

        List<ProductDto> productDtos = products.stream()
                .map(productMapper::productToDto)
                .toList();

        if (productDtos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productMapper.productToDto(product));
    }
}
