package com.miranda.orderapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineDTO {
	

	private Long id;
	//private Order order;
	private ProductDTO product;
	
	private Double price;
	
	private Double quantity;
	
	private Double total;

}
