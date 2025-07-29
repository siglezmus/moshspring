    package com.mosh.course.models;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Map;
    import java.util.Set;
    import java.util.UUID;

    @Getter
    @Setter
    @NoArgsConstructor
    @Entity
    @Table(name = "carts")
    public class Cart {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id")
        private UUID id;

        @Column(name = "date_created", insertable = false, updatable = false)
        private LocalDate dateCreated;

        @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
        private Set<CartItem> items = new HashSet<>();

        public BigDecimal getTotalPrice(){
            return items.stream()
                    .filter(item -> item.getProduct() != null && item.getProduct().getPrice() != null)
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        public CartItem getItem(Long productId){
            return items.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);
        }

        public CartItem addNewItem(Product product){

            var cartItem = getItem(product.getId());
            if (cartItem != null) cartItem.setQuantity(cartItem.getQuantity() + 1);
            else{
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setCart(this);
                items.add(cartItem);
            }
            return cartItem;

        }

        public void removeItem(Long productId){
            CartItem cartItem = getItem(productId);
            if(cartItem != null) {
                items.remove(cartItem);
                cartItem.setCart(null);
            }
        }

        public void clear(){
            items.clear();
        }
    }
