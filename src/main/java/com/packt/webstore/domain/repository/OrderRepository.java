package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Order;

public interface OrderRepository {
	void processOrder(String productId, long quantity);

	Long saveOrder(Order order);
}