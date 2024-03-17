package com.miranda.orderapi.converter;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miranda.orderapi.dto.OrderDTO;
import com.miranda.orderapi.dto.OrderLineDTO;
import com.miranda.orderapi.entity.Order;
import com.miranda.orderapi.entity.OrderLine;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OrderConverter implements UtilConverter<Order, OrderDTO> {

	private static final DateTimeFormatter DATET_DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("dd/MM/yyyy hh:mm:ss");
	private ProducConverter productConverter;
	private UserConverter userConverter;

	private List<OrderLineDTO> toDtoListT(List<OrderLine> lines) {
		if(lines ==null) return null;
		
		return lines.stream().map( line -> {
		return 	OrderLineDTO.builder()
			.id(line.getId())
			.price(line.getPrice())
			.product(productConverter.toDTO(line.getProduct()))
			.quantity(line.getQuantity())
			.total(line.getTotal())
			.build();
			
		}).toList();
	}
	
	private List<OrderLine> toEntityListT(List<OrderLineDTO> lines) {
		if(lines ==null) return null;
		
		return lines.stream().map( line -> 
		 	OrderLine.builder()
			.id(line.getId())
			.price(line.getPrice())
			.product(productConverter.toEntity(line.getProduct()))
			.quantity(line.getQuantity())
			.total(line.getTotal())
			.build()
			
		).toList();
	}

	@Override
	public OrderDTO toDTO(Order entity) {
		if (entity == null)
			return null;
		List<OrderLineDTO> lines = toDtoListT(entity.getLines());

		return OrderDTO.builder()
				.id(entity.getId())
				.lines(lines)
				.regDate(entity.getRegDate().format(DATET_DATE_TIME_FORMATTER))
				.total(entity.getTotal())
				.user(userConverter.toDTO(entity.getUser()))
				.build();
	}

	@Override
	public Order toEntity(OrderDTO dto) {
		if (dto == null)
			return null;
		List<OrderLine> lines = toEntityListT(dto.getLines());

		return Order.builder()
				.id(dto.getId())
				.lines(lines)
				.total(dto.getTotal())
				.user(userConverter.toEntity(dto.getUser()))
				.build();
	}

	

}
