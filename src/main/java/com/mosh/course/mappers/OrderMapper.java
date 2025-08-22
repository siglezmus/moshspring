package com.mosh.course.mappers;


import com.mosh.course.dtos.OrderDto;
import com.mosh.course.dtos.OrderResponse;
import com.mosh.course.dtos.ProductDto;
import com.mosh.course.models.Order;
import com.mosh.course.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse orderToDto(Order order);

    OrderDto toDto(Order order);

}
