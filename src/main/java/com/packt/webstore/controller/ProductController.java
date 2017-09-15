package com.packt.webstore.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ProductController {

	@RequestMapping("/products")
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/all")
	public String allProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/products/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String productCategory) {
		List<Product> products = productService.getProductsByCategory(productCategory);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping("/products/filter/{ByCriteria}")
	public String getProductsByFilter(Model model,
			@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}

	@RequestMapping("/products/filter/byPrize/{prize}")
	public String getProductsByPrize(Model model,
			@MatrixVariable(pathVar = "prize") Map<String, List<String>> filterParams) {
		model.addAttribute("products", productService.getProductsByPrize(filterParams));
		return "products";
	}

	@RequestMapping("/products/{category}/{prize}")
	public String getProductsByCategoryAndPrizeAndManufacturer(Model model,
			@PathVariable("category") String productCategory,
			@MatrixVariable(pathVar = "prize") Map<String, List<String>> filterParams,
			@RequestParam("manufacturer") String productManufacturer) {
		model.addAttribute("products",
				productService.getProductsByManPrize(filterParams, productCategory, productManufacturer));

		return "products";
	}

	@RequestMapping("/products/product")
	public String getProductById(Model model, @RequestParam("id") String productId) {
		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}

	@RequestMapping("/products/product-by-man")
	public String getProductsByManufacturer(Model model, @RequestParam("manufacturer") String productManufacturer) {
		model.addAttribute("products", productService.getProductsByManufacturer(productManufacturer));
		return "products";
	}

	@RequestMapping(value = "products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value = "products/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") @Valid Product newProduct,
			BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "addProduct";
		}
		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException(
					"Próba wiązania niedozwolonych pól: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		MultipartFile productImage = newProduct.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(
						new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Niepowodzenie podczas próby zapisu obrazka produktu", e);
			}
		}

		MultipartFile productInstruction = newProduct.getProductInstruction();
		if (productInstruction != null && !productInstruction.isEmpty()) {
			try {
				productInstruction.transferTo(
						new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".pdf"));
			} catch (Exception e) {
				throw new RuntimeException("Niepowodzenie podczas próby zapisu instrukcji produktu", e);
			}

		}

		productService.addProduct(newProduct);
		return "redirect:/products";
	}

	@RequestMapping("/products/invalidPromoCode")
	public String invalidPromoCode() {
		return "invalidPromoCode";
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}

	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setDisallowedFields("unitsInOrder", "discontinued");
		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category",
				"unitsInStock", "productImage", "productInstruction", "language");
		binder.setValidator(productValidator);
	}

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductValidator productValidator;
}
