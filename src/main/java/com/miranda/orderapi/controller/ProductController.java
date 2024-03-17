package com.miranda.orderapi.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miranda.orderapi.converter.ProducConverter;
import com.miranda.orderapi.dto.ProductDTO;
import com.miranda.orderapi.entity.Product;
import com.miranda.orderapi.service.ProductService;
import com.miranda.orderapi.utils.WrapperResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

	private ProductService productService;
	
	private ProducConverter converter;
	
	private WrapperResponse<ProductDTO> response;
	

	public ProductController(ProductService productService, 
			ProducConverter converter) {
		this.productService = productService;
		this.converter = converter;
		this.response = new WrapperResponse<>();
		this.response.setOk(true);
		this.response.setMessage("success");
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll(
			@RequestParam(required = false, defaultValue = "0") int pageNumber, 
			@RequestParam(required = false, defaultValue = "5")  int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Product> products = productService.getAll(pageable);
		List<ProductDTO> productsDTO = converter.toDtoList(products);
		//return response.createResponse(HttpStatus.OK);
		
		return new WrapperResponse<List<ProductDTO>>(true, "succes", productsDTO)
				.createResponse();
		 
	}

	@GetMapping("/{id}")
	public ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable Long id) {
		Product product = productService.findById(id);
		
		ProductDTO productDTO = converter.toDTO(product); 
		response.setBody(productDTO);

		return response.createResponse(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> newProduct(@RequestBody ProductDTO product) {
		Product nProduct = productService.save(converter.toEntity(product));
		ProductDTO productDTO = converter.toDTO(nProduct);
		response.setBody(productDTO);
		return response.createResponse(HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
		Product productUpdate = productService.save(converter.toEntity(product));
		ProductDTO productDTO = converter.toDTO(productUpdate);
		response.setBody(productDTO);
		return response.createResponse(HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<WrapperResponse<ProductDTO>> delete(@PathVariable Long id) {
		productService.delete(id);
		response.setBody(null);
		return response.createResponse(HttpStatus.OK);

	}

}
