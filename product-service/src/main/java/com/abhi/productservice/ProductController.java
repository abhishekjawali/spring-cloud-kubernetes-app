package com.abhi.productservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductMessages productMessage;

	@GetMapping(path = "/product/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable Integer id) {
		Products product = productService.getProduct(id);
		return new ResponseEntity<Products>(product, HttpStatus.OK);
	}

	@GetMapping(path = "/products")
	public ResponseEntity<List<Products>> getProducts() {
		List<Products> products = productService.getAllProducts();
		return new ResponseEntity<List<Products>>(products, HttpStatus.OK);
	}

	@GetMapping(path = "/message")
	public ResponseEntity<String> getDatasourceUrl() {

		return new ResponseEntity<String>(productMessage.getMessage(), HttpStatus.OK);
	}

}