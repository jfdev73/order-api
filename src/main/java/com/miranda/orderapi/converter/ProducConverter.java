package com.miranda.orderapi.converter;

import org.springframework.stereotype.Component;

import com.miranda.orderapi.dto.ProductDTO;
import com.miranda.orderapi.entity.Product;

@Component
public class ProducConverter implements UtilConverter<Product, ProductDTO>{

	@Override
	public ProductDTO toDTO(Product entity) {
		if(entity ==null) return null;
		return ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
	}

	@Override
	public Product toEntity(ProductDTO dto) {
		if(dto ==null) return null;
		return Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.price(dto.getPrice())
				.build();
	}

	
}
