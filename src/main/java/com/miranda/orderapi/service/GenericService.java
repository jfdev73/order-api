package com.miranda.orderapi.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface GenericService <T, ID>{
	
	T findById(ID id);
	
	List<T> getAll(Pageable pageable);
	
	void delete(ID id);
	
	T save(T t);
	
	
	

}
