package com.mosh.course.carts;

import com.mosh.course.products.ProductNotFoundException;
import com.mosh.course.products.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart(){
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.cartToDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) throw new ProductNotFoundException();

        var cartItem = cart.addNewItem(product);

        cartRepository.save(cart);
        return cartMapper.cartItemToDto(cartItem);
    }

    public List<CartDto> getAllCarts() {
        return cartRepository.findAllEager()
                .stream()
                .map(cartMapper::cartToDto)
                .toList();
    }

    public CartDto getCartById(UUID cartId){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();

        return cartMapper.cartToDto(cart);
    }

    public CartItemDto updateCartItemQuantity(UUID cartId, Long productId, Integer quantity){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();

        // Check if any CartItem in the cart references the product
        CartItem cartItem = cart.getItem(productId);
        if(cartItem == null) throw new ProductNotFoundException();

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.cartItemToDto(cartItem);
    }

    public void deleteCartItem(UUID cartId, Long productId){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(UUID cartId){
        var cart = cartRepository.findByIdEager(cartId).orElse(null);
        if (cart == null) throw new CartNotFoundException();
        if(cart.getItems().isEmpty()) return;
        cart.clear();
        cartRepository.save(cart);
    }
}
