package com.parsing.OrderFulfillmentSystem.Repo;

import com.parsing.OrderFulfillmentSystem.Entity.Product;
import com.parsing.OrderFulfillmentSystem.Entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, Long> {
}
