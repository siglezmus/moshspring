package com.mosh.course.orders;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    //OrderResponse orderToDto(Order order);

    OrderDto toDto(Order order);

}
