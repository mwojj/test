package com.packt.webstore.controller;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.beans.factory.annotation.Autowired;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.service.CustomerService;

@Controller
public class CustomerController {

	@RequestMapping("/customers")
	public String list(Model model) {
		model.addAttribute("customers", customerRepository.getAllCustomers());
		return "customers";
	}

	@Autowired
	private CustomerService customerRepository;

}
