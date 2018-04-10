package com.practice.service;

import com.practice.model.Product;

public interface ProductService {
	public Iterable<Product> getAllProducts();
	public Product getProductById(Long id);
	public Product saveProduct(Product product);
	public void deleteProduct(long id);

}
