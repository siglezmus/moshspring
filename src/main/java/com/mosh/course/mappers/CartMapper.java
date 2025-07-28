package com.mosh.course.mappers;

import com.mosh.course.dtos.CartDto;
import com.mosh.course.dtos.CartItemDto;
import com.mosh.course.models.Cart;
import com.mosh.course.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalCartPrice", expression = "java(cart.getTotalPrice())")
    CartDto cartToDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto cartItemToDto(CartItem cartItem);

}
