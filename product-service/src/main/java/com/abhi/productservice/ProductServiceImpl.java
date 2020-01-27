package com.abhi.productservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public Products getProduct(Integer productId) {
		return productRepo.findByProductId(productId);
	}

	@Override
	public List<Products> getAllProducts() {
		List<Products> products = (List<Products>) productRepo.findAll();
		return products;
	}
}