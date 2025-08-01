package com.mosh.course.repositories;

import com.mosh.course.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.id FROM Category c")
    Set<Short> findAllCategoryIds();
}
