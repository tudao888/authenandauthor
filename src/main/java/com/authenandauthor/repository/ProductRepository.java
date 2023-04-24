package com.authenandauthor.repository;

import com.authenandauthor.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    Product findProductByNameContains(String name);

    Product findById(int id);

}
