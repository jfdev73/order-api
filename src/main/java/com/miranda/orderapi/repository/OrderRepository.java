package com.miranda.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miranda.orderapi.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
