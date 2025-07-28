package com.mosh.course.controllers;

import com.mosh.course.dtos.AddItemToCartRequest;
import com.mosh.course.dtos.CartDto;
import com.mosh.course.dtos.CartItemDto;
import com.mosh.course.mappers.CartMapper;
import com.mosh.course.models.Cart;
import com.mosh.course.models.CartItem;
import com.mosh.course.repositories.CartRepository;
import com.mosh.course.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        var carts = cartRepository.findAllEager()
                .stream()
                .map(cartMapper::cartToDto)
                .toList();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID id) {
        var cart = cartRepository.findByIdEager(id).orElse(null);
        if (cart == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cartMapper.cartToDto(cart));
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        Cart cart = new Cart();
        cartRepository.save(cart);
        var dto = cartMapper.cartToDto(cart);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) return ResponseEntity.notFound().build();

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) return ResponseEntity.badRequest().build();

        var cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.cartItemToDto(cartItem));
    }
}
