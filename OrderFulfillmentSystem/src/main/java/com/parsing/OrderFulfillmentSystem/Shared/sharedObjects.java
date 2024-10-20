package com.parsing.OrderFulfillmentSystem.Shared;

import com.parsing.OrderFulfillmentSystem.Model.InvoiceModel;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Model.ProductModel;
import com.parsing.OrderFulfillmentSystem.Model.ShipmentModel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public enum sharedObjects {

    INSTANCE;

    public final BlockingQueue<OrderModel> orderQueue =new ArrayBlockingQueue<>(1000);
    public final BlockingQueue<ProductModel> productQueue =new ArrayBlockingQueue<>(1000);
    public final BlockingQueue<ShipmentModel> shipmentQueue =new ArrayBlockingQueue<>(1000);
    public final BlockingQueue<InvoiceModel> invoiceQueue =new ArrayBlockingQueue<>(1000);

    public final Deque<String> orderFiles = new ArrayDeque<>();
    public final Deque<String> productFiles = new ArrayDeque<>();
    public final Deque<String> shipmentFiles = new ArrayDeque<>();
    public final Deque<String> invoiceFiles = new ArrayDeque<>();


    public boolean isReadingOrderFiles=false;
    public boolean isReadingProductFiles=false;
    public boolean isReadingShipmentFiles=false;
    public boolean isReadingInvoiceFiles=false;


}
