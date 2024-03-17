package com.miranda.orderapi.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miranda.orderapi.converter.OrderConverter;
import com.miranda.orderapi.dto.OrderDTO;
import com.miranda.orderapi.dto.ProductDTO;
import com.miranda.orderapi.entity.Order;
import com.miranda.orderapi.service.OrderService;
import com.miranda.orderapi.utils.WrapperResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private OrderConverter converter;
	
	private OrderService service;
	
	public OrderController(OrderConverter converter,OrderService service) {
		this.converter = converter;
		this.service = service;
		
	}

	@GetMapping("/all")
	public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAll(
			@RequestParam(required = false, defaultValue = "0") int pageNumber,
			@RequestParam(required = false, defaultValue = "5") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Order> orders = service.getAll(pageable);
		return new WrapperResponse<>(converter.toDtoList(orders)).createResponse();

	}

	@GetMapping("/{id}")
	public ResponseEntity<WrapperResponse<OrderDTO>> findById(@PathVariable Long id) {
		Order oder = service.findById(id);
		return new WrapperResponse<>(converter.toDTO(oder)).createResponse(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<WrapperResponse<OrderDTO>> newOrder(@RequestBody OrderDTO order) {
		Order nOrder = service.save(converter.toEntity(order));

		return new WrapperResponse<>(converter.toDTO(nOrder)).createResponse(HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<WrapperResponse<OrderDTO>> updateProduct(@RequestBody OrderDTO order) {
		Order nOrder = service.save(converter.toEntity(order));

		return new WrapperResponse<>(converter.toDTO(nOrder)).createResponse(HttpStatus.OK);

	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return new WrapperResponse<>(null).createResponse(HttpStatus.OK);

	}

}
