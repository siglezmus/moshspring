package com.mosh.course.mappers;

import com.mosh.course.dtos.ProductDto;
import com.mosh.course.dtos.UpdateUserRequest;
import com.mosh.course.models.Category;
import com.mosh.course.models.Product;
import com.mosh.course.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categoryId", source = "category.id")
    ProductDto productToDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto dto);

    @Mapping(target = "category", ignore = true)
    void update(ProductDto request, @MappingTarget Product product);

}
