package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Entity.Order;
import com.parsing.OrderFulfillmentSystem.Entity.Product;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Model.ProductModel;
import com.parsing.OrderFulfillmentSystem.Repo.OrderRepo;
import com.parsing.OrderFulfillmentSystem.Repo.ProductRepo;
import com.parsing.OrderFulfillmentSystem.Service.BbOperationsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.parsing.OrderFulfillmentSystem.Shared.sharedObjects.INSTANCE;

@Service
public class BbOperationsServiceImpl implements BbOperationsService {


    List<Order> orderList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();


    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    public BbOperationsServiceImpl(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @Override
    public void addOrder() {
        try {
            System.out.println("Starting to add orders.");
            while (!INSTANCE.orderQueue.isEmpty()){
               OrderModel orderModel= INSTANCE.orderQueue.take();
               Order order = createOrder(orderModel);
               orderList.add(order);
                System.out.println("Added order to the list: ID = " + orderModel.getCustomerName());
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
            productRepo.saveAll(productList);
        }).exceptionally((ex)->{
            System.out.println("Error while inserting connections");
            return null;
        });
    }

}
