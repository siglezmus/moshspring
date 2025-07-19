package com.mosh.course.mappers;

import com.mosh.course.dtos.ProductDto;
import com.mosh.course.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto productToDto(Product product);
}
