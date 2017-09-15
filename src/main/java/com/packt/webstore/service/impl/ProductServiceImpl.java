package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packt.webstore.*;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {

		return productRepository.getAllProducts();

	}

	public List<Product> getProductsByCategory(String category) {
		return productRepository.getProductsByCategory(category);
	}
	
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		return productRepository.getProductsByFilter(filterParams);
		}
	
	public Set<Product> getProductsByPrize(Map<String, List<String>> filterParams) {
		return productRepository.getProductsByPrize(filterParams);
		}

	public Product getProductById(String ProductID) {
		return productRepository.getProductById(ProductID);
	}

	public List<Product> getProductsByManufacturer(String manufacturer) {
		return productRepository.getProductsByManufacturer(manufacturer);
	}

	
	public Set<Product> getProductsByManPrize(Map<String, List<String>> filterParams, String productCategory, String productManufacturer) {
		return productRepository.getProductsByManPrize(filterParams, productCategory, productManufacturer);
	}
	
	public void addProduct(Product product) {
		productRepository.addProduct(product);
		}
}
