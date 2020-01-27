package com.abhi.productservice;

import java.util.List;

public interface ProductService {

	public Products getProduct(Integer productId);

	public List<Products> getAllProducts();

}