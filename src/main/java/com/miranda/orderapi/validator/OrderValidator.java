package com.miranda.orderapi.validator;

import com.miranda.orderapi.entity.Order;
import com.miranda.orderapi.entity.OrderLine;
import com.miranda.orderapi.exceptions.ValidateServiceException;

public class OrderValidator {
	
	public static void save(Order order) {
		
		
		if(order.getLines() == null || order.getLines().isEmpty()){
			throw new ValidateServiceException ("Las lineas son requeridas");
		}
		
		for(OrderLine line: order.getLines()) {
			
			if(line.getProduct() == null || line.getProduct().getId() == null) 
				throw new ValidateServiceException ("El producto es requerido");
			if(line.getQuantity() == null) {
				throw new ValidateServiceException ("La cantidad es requerida");
				}
			if(line.getQuantity() < 0) {
				throw new ValidateServiceException ("La cantidad no es valida");
			}
			
		}
		
	}

}
