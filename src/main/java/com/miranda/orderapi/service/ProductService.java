package com.miranda.orderapi.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miranda.orderapi.entity.Product;
import com.miranda.orderapi.exceptions.GeneralServiceException;
import com.miranda.orderapi.exceptions.NoDataFoundException;
import com.miranda.orderapi.exceptions.ValidateServiceException;
import com.miranda.orderapi.repository.ProductRepository;
import com.miranda.orderapi.validator.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService implements GenericService<Product, Long> {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product findById(Long id) {
		try {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe el producto"));
			return product;

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
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe el producto"));
			productRepository.delete(product);

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}

	}

	@Override
	public List<Product> getAll(Pageable pageable) {
		try {
			List<Product> products = productRepository.findAll(pageable).toList();
			return products;

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
	public Product save(Product product) {
		try {
			ProductValidator.save(product);

			if (product.getId() != null) {
				productRepository.findById(product.getId())
						.orElseThrow(() -> new NoDataFoundException("No existe el producto"));
			}

			Product productoSave = productRepository.save(product);
			return productoSave;

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException();
		}

		/*
		 * if(product.getId()==null) { Product newProduct
		 * =productRepository.save(product); return newProduct; }
		 * productRepository.findById(product.getId()) .orElseThrow(() -> new
		 * RuntimeException("No existe el producto")); Product updateProduct
		 * =productRepository.save(product);
		 * 
		 * return updateProduct;
		 */

	}

	/*
	 * private Product update(Product product) { Product productUpdate =
	 * productRepository.findById(product.getId()) .orElseThrow(() -> new
	 * RuntimeException("No existe el producto"));
	 * productRepository.save(productUpdate); return productUpdate; }
	 */

}
