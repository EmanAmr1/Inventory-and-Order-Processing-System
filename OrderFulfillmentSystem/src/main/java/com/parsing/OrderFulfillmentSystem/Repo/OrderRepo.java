package com.parsing.OrderFulfillmentSystem.Repo;

import com.parsing.OrderFulfillmentSystem.Entity.Order;
import com.parsing.OrderFulfillmentSystem.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
