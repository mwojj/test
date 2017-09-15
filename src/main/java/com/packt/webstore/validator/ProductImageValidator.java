package com.packt.webstore.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.packt.webstore.domain.Product;

@Component
public class ProductImageValidator implements Validator {
	long allowedSize = 600;
	
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		if (product.getProductImage().getSize() > allowedSize) {
			errors.rejectValue("productImage", "com.packt.webstore.validator.ProductImageValidator.message");
		}
	}
}
