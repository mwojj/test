package com.packt.webstore.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductRepository {
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByManufacturer(String manufacturer);
	Product getProductById(String ProductID);
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	Set<Product> getProductsByPrize(Map<String, List<String>> filterParams);
	Set<Product> getProductsByManPrize(Map<String, List<String>> filterParams, String productCategory, String productManufacturer);
	void addProduct(Product product);
	
}
