package com.miranda.orderapi.converter;

import java.util.List;

public  interface UtilConverter <E,DTO>{
	
	 DTO toDTO(E entity);
	
	 E toEntity(DTO dto);
	
	default List<DTO> toDtoList( List<E> entitys){
		//return entitys.stream().map(e -> toDTO(e)).toList();
		if(entitys==null) return null;
		return entitys.stream().map(this::toDTO).toList();
	}
	
	
	default List<E> toEntityList( List<DTO> dtos){
		//return dtos.stream().map(e -> toEntity(e)).toList();
		if(dtos==null) return null;
		return dtos.stream().map(this::toEntity).toList();
	}
	

}
