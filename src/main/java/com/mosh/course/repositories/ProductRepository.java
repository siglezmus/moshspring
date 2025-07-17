package com.mosh.course.repositories;

import com.mosh.course.dtos.ProductSummary;
import com.mosh.course.dtos.ProductSummaryDTO;
import com.mosh.course.models.Category;
import com.mosh.course.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository, JpaSpecificationExecutor<Product> {

    List<Product> findByName(String name);

    @Query("select p from Product p join p.category where p.price between :min and :max order by p.name")
    List<Product> findProducts(@Param("min") BigDecimal min,@Param("max") BigDecimal max);

    @Query("select count(*) from Product p where p.price between :min and :max")
    long countProducts(@Param("min") BigDecimal min,@Param("max") BigDecimal max);

    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(BigDecimal newPrice, Short categoryId);

    @Query("select new com.mosh.course.dtos.ProductSummaryDTO(p.id, p.name) from Product p where p.category = :category")
    List<ProductSummaryDTO> findByCategory(@Param("category") Category category);
}
