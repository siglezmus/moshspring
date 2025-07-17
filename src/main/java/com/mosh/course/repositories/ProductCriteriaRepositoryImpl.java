package com.mosh.course.repositories;

import com.mosh.course.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@PersistenceContext
@AllArgsConstructor
@Repository
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository{

    private final EntityManager entityManager;

    @Override
    public List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice, Short categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null){
            // name like %
            predicates.add(cb.like(root.get("name"), "%"+name+"%"));
        }
        if(minPrice != null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if(maxPrice != null){
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if(categoryId != null){
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }
        cq.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(cq).getResultList();
    }
}
