package com.abhi.productservice;

import org.springframework.data.repository.CrudRepository;

//@Repository
public interface ProductRepo extends CrudRepository<Products, Integer> {
    Products findByProductId(Integer id);
}