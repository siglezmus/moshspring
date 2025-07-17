package com.mosh.course.Services;

import com.mosh.course.models.Category;
import com.mosh.course.models.Product;
import com.mosh.course.repositories.CategoryRepository;
import com.mosh.course.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductCategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void saveNewProductToNewCategory(){

        Product product = Product.builder()
                .name("lego")
                .price(11.5D)
                .build();
        Category category = Category.builder()
                .name("toys")
                .build();
        product.setCategory(category);
        productRepository.save(product);

    }
    @Transactional
    public void saveNewProductToExistingCategory(){
        Product product = Product.builder()
                .name("lego")
                .price(11.5D)
                .build();
        Category category = categoryRepository.findById(1L).orElseThrow();
        product.setCategory(category);
        productRepository.save(product);
    }

    public void deleteProduct(){
        Product product = productRepository.findById(3L).orElseThrow();
        productRepository.delete(product);
    }
}
