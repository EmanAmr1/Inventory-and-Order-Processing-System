package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Entity.Invoice;
import com.parsing.OrderFulfillmentSystem.Entity.Order;
import com.parsing.OrderFulfillmentSystem.Entity.Product;
import com.parsing.OrderFulfillmentSystem.Entity.Shipment;
import com.parsing.OrderFulfillmentSystem.Model.InvoiceModel;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Model.ProductModel;
import com.parsing.OrderFulfillmentSystem.Model.ShipmentModel;
import com.parsing.OrderFulfillmentSystem.Repo.InvoiceRepo;
import com.parsing.OrderFulfillmentSystem.Repo.OrderRepo;
import com.parsing.OrderFulfillmentSystem.Repo.ProductRepo;
import com.parsing.OrderFulfillmentSystem.Repo.ShipmentRepo;
import com.parsing.OrderFulfillmentSystem.Service.DBOperationsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.parsing.OrderFulfillmentSystem.Shared.sharedObjects.INSTANCE;

@Service
public class DBOperationsServiceImpl implements DBOperationsService {


    List<Order> orderList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    List<Shipment> shipmentList = new ArrayList<>();
    List<Invoice> invoiceList = new ArrayList<>();


    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final ShipmentRepo shipmentRepo;
    private final InvoiceRepo invoiceRepo;
    public DBOperationsServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, ShipmentRepo shipmentRepo, InvoiceRepo invoiceRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.shipmentRepo = shipmentRepo;
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    public void addOrder() {
        try {
            System.out.println("Starting to add orders.");
            while (!INSTANCE.orderQueue.isEmpty()){
               OrderModel orderModel= INSTANCE.orderQueue.take();
               Order order = createOrder(orderModel);
               orderList.add(order);
                  if (orderList.size()==1000)
                  {
                      pushOrderToDB();
                  }
            } if (!orderList.isEmpty()){
                System.out.println("Pushing remaining {} nodes into the database."+ orderList.size());
                pushOrderToDB();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Order createOrder(OrderModel orderModel){
        Order order = Order
                .builder()
                .status(orderModel.getStatus())
                .orderDate(orderModel.getOrderDate())
                .productId(orderModel.getProductId())
                .customerName(orderModel.getCustomerName())
                .quantity(orderModel.getQuantity())
                .build();

        return order;
    }

    private void pushOrderToDB(){
        List<Order> newOrderList = orderList;
        orderList = new ArrayList<>();

        CompletableFuture.runAsync(()-> {
            orderRepo.saveAll(newOrderList);})
                .exceptionally((ex)->{
                    System.out.println("Error while inserting connections");
                    return null;
                });
    }

    //////////////////////////////////////////////////////////////

    @Override
    public void addProduct() {
      try {

          System.out.println("Starting to add product.");
          while (!INSTANCE.productQueue.isEmpty()){
              ProductModel productModel = INSTANCE.productQueue.take();
              Product product=createProduct(productModel);
              productList.add(product);
              if (productList.size()==1000){
                  pushProductToDB();
              }
          }if (!productList.isEmpty()){
              System.out.println("Pushing remaining {} nodes into the database."+ productList.size());
              pushProductToDB();
          }
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private Product createProduct(ProductModel productModel){
        Product product =Product
                .builder()
                .stock(productModel.getStock())
                .price(productModel.getPrice())
                .description(productModel.getDescription())
                .name(productModel.getName())
                .build();
        return product;
    }

    private void pushProductToDB(){
        List<Product> newProductList = productList;
        productList = new ArrayList<>();
        CompletableFuture.runAsync(()-> {
            productRepo.saveAll(newProductList);
        }).exceptionally((ex)->{
            ex.printStackTrace();
            System.out.println("Error while inserting connections");
            return null;
        });
    }

    ////////////////////////////////////////////////////////////

    @Override
    public void addShipment() {
        try {
            System.out.println("Starting to add shipment.");
            while (!INSTANCE.shipmentQueue.isEmpty()){
                ShipmentModel shipmentModel = INSTANCE.shipmentQueue.take();
                Shipment shipment = createShipment(shipmentModel);
                shipmentList.add(shipment);
                if (shipmentList.size()==1000){
                    pushShipmentToDB();
                }
            }if (!shipmentList.isEmpty()){
                System.out.println("Pushing remaining {} nodes into the database."+ shipmentList.size());
                pushShipmentToDB();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Shipment createShipment(ShipmentModel shipmentModel){
        Shipment shipment =Shipment
                .builder()
                .shipmentDate(shipmentModel.getShipmentDate())
                .carrier(shipmentModel.getCarrier())
                .trackingNumber(shipmentModel.getTrackingNumber())
                .status(shipmentModel.getStatus())
                .orderId(shipmentModel.getOrderId())
                .build();
        return shipment;
    }

   private void pushShipmentToDB(){
        List<Shipment> newShipmentList = shipmentList;
        shipmentList = new ArrayList<>();
        CompletableFuture.runAsync(()-> {
            shipmentRepo.saveAll(shipmentList);
        }).exceptionally((ex)->{
            System.out.println("Error while inserting connections");
            return null;
        });
   }


   ///////////////////////////////////////////////////////////////////////


    @Override
    public void addInvoice() {
        try {
            System.out.println("Starting to add invoice.");
            while (!INSTANCE.invoiceQueue.isEmpty()){
                InvoiceModel invoiceModel = INSTANCE.invoiceQueue.take();
                Invoice invoice = createInvoice(invoiceModel);
                invoiceList.add(invoice);
                if (invoiceList.size()==1000){
                    pushInvoiceToDB();
                }
            }if (!invoiceList.isEmpty()){
                System.out.println("Pushing remaining {} nodes into the database."+ invoiceList.size());
                pushInvoiceToDB();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Invoice createInvoice(InvoiceModel invoiceModel){
        Invoice invoice =Invoice
                .builder()
                .invoiceDate(invoiceModel.getInvoiceDate())
                .orderId(invoiceModel.getOrderId())
                .total(invoiceModel.getTotal())
                .paymentStatus(invoiceModel.getPaymentStatus())
                .build();

        return invoice;
    }

    private void pushInvoiceToDB(){
        List<Invoice> newInvoiceList = invoiceList;
        invoiceList = new ArrayList<>();
        CompletableFuture.runAsync(()-> {
            invoiceRepo.saveAll(newInvoiceList);
        }).exceptionally((ex)->{
            System.out.println("Error while inserting connections");
            return null;
        });
    }

}
