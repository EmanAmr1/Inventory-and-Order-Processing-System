package com.parsing.OrderFulfillmentSystem.Repo;

import com.parsing.OrderFulfillmentSystem.Entity.Invoice;
import com.parsing.OrderFulfillmentSystem.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
}
