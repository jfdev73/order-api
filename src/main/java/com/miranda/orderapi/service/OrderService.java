package com.miranda.orderapi.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.miranda.orderapi.entity.Order;
import com.miranda.orderapi.entity.OrderLine;
import com.miranda.orderapi.entity.Product;
import com.miranda.orderapi.entity.User;
import com.miranda.orderapi.exceptions.GeneralServiceException;
import com.miranda.orderapi.exceptions.NoDataFoundException;
import com.miranda.orderapi.exceptions.ValidateServiceException;
import com.miranda.orderapi.repository.OrderLineRepository;
import com.miranda.orderapi.repository.OrderRepository;
import com.miranda.orderapi.repository.ProductRepository;
import com.miranda.orderapi.security.UserPrincipal;
import com.miranda.orderapi.validator.OrderValidator;
import com.miranda.orderapi.validator.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService implements GenericService<Order, Long> {

	private OrderRepository repository;
	
	private OrderLineRepository repositoryOrderLine;
	
	private ProductRepository repositoryProduct;

	public OrderService(OrderRepository repository,OrderLineRepository repositoryOrderLine,
			ProductRepository repositoryProduct) {
		this.repository = repository;
		this.repositoryOrderLine = repositoryOrderLine;
		this.repositoryProduct = repositoryProduct;

	}

	@Override
	public Order findById(Long id) {
		try {
			return repository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe la orden"));

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}
	}

	@Override
	public List<Order> getAll(Pageable pageable) {
		try {
			return repository.findAll(pageable).toList();

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}

	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		try {
			Order order= repository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe la orden"));
			repository.delete(order);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}

	}
	
	@Transactional
	@Override
	public Order save(Order order) {
		try {
			
			OrderValidator.save(order);
			User user = UserPrincipal.getCurrentUser();
			double total =0;
			for(OrderLine line: order.getLines()) {
				Product product= repositoryProduct.findById(line.getProduct().getId())
				.orElseThrow(() -> new  NoDataFoundException("No existe el produtco: "+line.getProduct().getId()));
				line.setPrice(product.getPrice());
				line.setTotal(product.getPrice() * line.getQuantity());
				total+= line.getTotal();
				
			}
			order.setTotal(total);
			order.getLines().forEach(line-> line.setOrder(order));

			if (order.getId() == null) {
				order.setUser(user);
				order.setRegDate(LocalDateTime.now());
				return repository.save(order);
				
			}
			Order orderUpdate= repository.findById(order.getId())
			.orElseThrow(() -> new NoDataFoundException("No existe la orden"));
			order.setRegDate(orderUpdate.getRegDate());
			log.error("lineas que vienen en la orden");
			System.out.println(order.getLines());
			
		
			List<OrderLine> deletedLines = orderUpdate.getLines();
		
			deletedLines.removeAll(order.getLines());
		
			repositoryOrderLine.deleteAll(deletedLines);
			return repository.save(order);
			

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}
	}

}
