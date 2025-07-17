package com.mosh.course.repositories;

import com.mosh.course.models.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice, Short categoryId);
}
