package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository {
	private List<Product> listOfProducts = new ArrayList<Product>();

	public InMemoryProductRepository() {
		Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
		iphone.setDescription(
				"Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczości 6401136 i 8-megapikselowym aparatem");
		iphone.setCategory("Smartfon");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		Product laptop_dell = new Product("P1235", "Dell Inspiron", new BigDecimal(700));
		laptop_dell.setDescription("Dell Inspiron, 14-calowy laptop (czarny) z procesorem Intel Core 3. generacji");
		laptop_dell.setCategory("Laptop");
		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);
		Product tablet_Nexus = new Product("P1236", "Nexus 7", new BigDecimal(300));
		tablet_Nexus.setDescription(
				"Google Nexus 7 jest najlżejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro");
		tablet_Nexus.setCategory("Tablet");
		tablet_Nexus.setManufacturer("Google");
		tablet_Nexus.setUnitsInStock(1000);
		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_Nexus);
	}

	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	public List<Product> getProductsByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();
		for (Product product : listOfProducts) {
			if (category.equalsIgnoreCase(product.getCategory())) {
				productsByCategory.add(product);
			}
		}
		return productsByCategory;
	}

	public List<Product> getProductsByUnitPrice(String unitPrice) {
		List<Product> productsByUnitPrice = new ArrayList<Product>();
		for (Product product : listOfProducts) {
			if (unitPrice.equals(product.getUnitPrice())) {
				productsByUnitPrice.add(product);
			}
		}
		return productsByUnitPrice;
	}

	public Product getProductById(String productId) {
		Product productById = null;
		for (Product product : listOfProducts) {
			if (product != null && product.getProductId() != null && product.getProductId().equals(productId)) {
				productById = product;
				break;
			}
		}
		if (productById == null) {
			throw new ProductNotFoundException(productId);
		}
		return productById;
	}

	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();
		Set<Product> productsByManufacturer = new HashSet<Product>();
		Set<Product> productsByUnitPrice = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();
		if (criterias.contains("brand")) {
			for (String brandName : filterParams.get("brand")) {
				for (Product product : listOfProducts) {
					if (brandName.equalsIgnoreCase(product.getManufacturer())) {
						productsByBrand.add(product);
					}
				}
			}
		}
		if (criterias.contains("category")) {
			for (String category : filterParams.get("category")) {
				productsByCategory.addAll(this.getProductsByCategory(category));
			}
		}
		productsByCategory.retainAll(productsByBrand);
		return productsByCategory;
	}

	public Set<Product> getProductsByPrize(Map<String, List<String>> filterParams) {
		Set<Product> productsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();
		Set<Product> productsByManufacturer = new HashSet<Product>();
		Set<Product> productsByUnitPrice = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();
		if (criterias.contains("manufacturer")) {
			for (String brandName : filterParams.get("manufacturer")) {
				for (Product product : listOfProducts) {
					if (brandName.equalsIgnoreCase(product.getManufacturer())) {
						productsByBrand.add(product);
					}
				}
			}
			System.out.println("By brand: " + productsByBrand);
		}
		if (criterias.contains("category")) {
			for (String category : filterParams.get("category")) {
				productsByCategory.addAll(this.getProductsByCategory(category));
			}
			System.out.println("By category: " + productsByCategory);
		}

		if (criterias.contains("high") && criterias.contains("low")) {
			for (String unitPriceLow : filterParams.get("low")) {
				int low = Integer.parseInt(unitPriceLow);

				for (String unitPriceHigh : filterParams.get("high")) {
					int high = Integer.parseInt(unitPriceHigh);

					for (Product product : listOfProducts) {
						if (product.getUnitPrice().intValueExact() <= high
								&& product.getUnitPrice().intValueExact() >= low) {
							productsByUnitPrice.add(product);

						}
					}

				}
			}
		} else if (criterias.contains("high") || criterias.contains("low")) {
			if (criterias.contains("low")) {
				for (String unitPrice : filterParams.get("low")) {
					int low = Integer.parseInt(unitPrice);

					for (Product product : listOfProducts) {
						if (product.getUnitPrice().intValueExact() > low) {
							productsByUnitPrice.add(product);

						}
					}
				}
			}

			if (criterias.contains("high")) {
				for (String unitPrice : filterParams.get("high")) {
					int high = Integer.parseInt(unitPrice);

					for (Product product : listOfProducts) {
						if (product.getUnitPrice().intValueExact() < high) {
							productsByUnitPrice.add(product);

						}

					}
				}
			}
		}

		System.out.println("By unit price: " + productsByUnitPrice);
		return productsByUnitPrice;
	}

	public List<Product> getProductsByManufacturer(String manufacturer) {
		List<Product> productsByManufacturer = new ArrayList<Product>();
		for (Product product : listOfProducts) {
			if (manufacturer.equalsIgnoreCase(product.getManufacturer())) {
				productsByManufacturer.add(product);
			}
		}
		return productsByManufacturer;
	}

	public Set<Product> getProductsByManPrize(Map<String, List<String>> filterParams, String productCategory,
			String productManufacturer) {
		Set<Product> productsByManPrize = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();

		List<Product> productsByCategory = new ArrayList<Product>();
		List<Product> productsByManufacturer = new ArrayList<Product>();

		for (Product product : listOfProducts) {
			if (productCategory.equalsIgnoreCase(product.getCategory())) {
				productsByCategory.add(product);

				for (Product producte : productsByCategory) {
					if (productManufacturer.equalsIgnoreCase(product.getManufacturer())) {
						productsByManufacturer.add(producte);

						if (criterias.contains("high") && criterias.contains("low")) {
							for (String unitPriceLow : filterParams.get("low")) {
								int low = Integer.parseInt(unitPriceLow);

								for (String unitPriceHigh : filterParams.get("high")) {
									int high = Integer.parseInt(unitPriceHigh);

									for (Product products : productsByManufacturer) {
										if (products.getUnitPrice().intValueExact() <= high
												&& products.getUnitPrice().intValueExact() >= low) {
											productsByManPrize.add(products);

										}
									}

								}
							}
						} else if (criterias.contains("high") || criterias.contains("low")) {
							if (criterias.contains("low")) {
								for (String unitPrice : filterParams.get("low")) {
									int low = Integer.parseInt(unitPrice);

									for (Product products : productsByManufacturer) {
										if (products.getUnitPrice().intValueExact() > low) {
											productsByManPrize.add(products);

										}
									}
								}
							}

							if (criterias.contains("high")) {
								for (String unitPrice : filterParams.get("high")) {
									int high = Integer.parseInt(unitPrice);

									for (Product products : productsByManufacturer) {
										if (products.getUnitPrice().intValueExact() < high) {
											productsByManPrize.add(products);

										}

									}
								}
							}
						}
					}
				}
			}

		}

		return productsByManPrize;
	}

	public void addProduct(Product product) {
		listOfProducts.add(product);
		}
	
	
	
}