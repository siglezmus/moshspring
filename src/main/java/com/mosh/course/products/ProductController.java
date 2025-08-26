package com.mosh.course.products;

import com.mosh.course.models.Category;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Tag(name = "Products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(required = false, defaultValue = "", name = "categoryId") Short categoryId
    ) {
        List<Product> products;
        if(categoryRepository.findAllCategoryIds().contains(categoryId))
            products = productRepository.findByCategoryId(categoryId);
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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder){
        Product product = productMapper.toEntity(request);
        Category category = categoryRepository.findById(request.getCategoryId().longValue()).orElse(null);
        product.setCategory(category);
        productRepository.save(product);
        var dto = productMapper.productToDto(product);
        dto.setId(product.getId());
        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request) {

        var product = productRepository.findById(id).orElse(null);
        if (product == null) return ResponseEntity.notFound().build();

        // Handle each field conditionally
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());

        if (request.getCategoryId() != null) {
            var category = categoryRepository.findById(request.getCategoryId().longValue()).orElse(null);
            product.setCategory(category);
        }

        productRepository.save(product);
        return ResponseEntity.ok(productMapper.productToDto(product));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request){
        var product = productRepository.findById(id).orElse(null);
        if(product == null) return ResponseEntity.notFound().build();

        productRepository.delete(product);

        return ResponseEntity.noContent().build();
    }


}
