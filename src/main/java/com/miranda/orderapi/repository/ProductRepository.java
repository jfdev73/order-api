package com.miranda.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miranda.orderapi.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
