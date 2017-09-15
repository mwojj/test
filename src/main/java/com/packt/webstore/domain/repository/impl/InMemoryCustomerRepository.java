package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.CustomerRepository;
@Repository
public class InMemoryCustomerRepository implements CustomerRepository{

	private List<Customer> listOfCustomers = new ArrayList<Customer>();
	
	public InMemoryCustomerRepository() {
		
		Customer klient1 = new Customer("C1", "Bob", "ul. Kliencka 90", 10);
		Customer klient2 = new Customer("C2", "Rob", "ul. Kliencka 90", 0);
		Customer klient3 = new Customer("C3", "Tob", "ul. Kliencka 90", 98);
		Customer klient4 = new Customer("C4", "Gob", "ul. Kliencka 90", 11);
	
		klient1.setCustomerId("K1");
		klient2.setCustomerId("K2");
		klient3.setCustomerId("K3");
		klient4.setCustomerId("K4");
		
		listOfCustomers.add(klient1);
		listOfCustomers.add(klient2);
		listOfCustomers.add(klient3);
		listOfCustomers.add(klient4);
		
	}
	
	
	
	
	public List<Customer> getAllCustomers() {
		
		return listOfCustomers;
	}

	public Customer getCustomerById(String customerId) {
		Customer customerById = null;
		for (Customer customer : listOfCustomers) {
			if (customer != null && customer.getCustomerId() != null && customer.getCustomerId().equals(customerId)) {
				customerById = customer;
				break;
			}
		}
		if (customerById == null) {
			throw new IllegalArgumentException("Brak produktu o wskazanym id: " + customerId);
		}
		return customerById;
	}
	
	
}
