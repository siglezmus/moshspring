package com.mosh.course.repositories;

import com.mosh.course.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategoryId(Short categoryId);

}
