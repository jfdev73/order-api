package com.miranda.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miranda.orderapi.entity.OrderLine;

public interface OrderLineRepository  extends JpaRepository<OrderLine, Long>{

}
