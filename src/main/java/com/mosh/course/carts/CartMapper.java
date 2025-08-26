package com.mosh.course.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalCartPrice", expression = "java(cart.getTotalPrice())")
    CartDto cartToDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto cartItemToDto(CartItem cartItem);


}
