
package com.packt.webstore.validator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

public class CategoryValidator implements ConstraintValidator<Category, String> {

	@Autowired
	private ProductService productService;

	public void initialize(Category constraintAnnotation) {
		// Celowo pozostawione puste; w tym miejscu nale�y zainicjowa� adnotacj�
		// ograniczaj�c�
		// do sensownych domy�lnych warto�ci.
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<Product> product;
		List<String> allowedCategories = new ArrayList<String>();
		allowedCategories.add("Laptop");
		allowedCategories.add("Smartfon");
		allowedCategories.add("Tablet");

		product = productService.getAllProducts();

		if (product.equals(allowedCategories)) {
			return false;
		} else {
			return true;
		}

	}

}
