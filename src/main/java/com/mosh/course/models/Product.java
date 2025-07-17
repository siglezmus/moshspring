package com.mosh.course.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToMany(mappedBy = "wishlist")
    @ToString.Exclude
    @Builder.Default
    private Set<User> users = new HashSet<>();

    public void addCategory(Category categoryArg){
        category = categoryArg;
        categoryArg.addProduct(this);
    }

    public void removeCategory(Category categoryArg){
        category = null;
        categoryArg.removeProduct(this);
    }
}
