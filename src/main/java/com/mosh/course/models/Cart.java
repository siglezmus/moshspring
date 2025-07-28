    package com.mosh.course.models;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.HashSet;
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

        @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE)
        private Set<CartItem> items = new HashSet<>();

        public BigDecimal getTotalPrice(){
            return items.stream()
                    .filter(item -> item.getProduct() != null && item.getProduct().getPrice() != null)
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
