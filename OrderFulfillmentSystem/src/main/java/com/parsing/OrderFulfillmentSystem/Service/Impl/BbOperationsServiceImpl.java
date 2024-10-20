package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Entity.Order;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Repo.OrderRepo;
import com.parsing.OrderFulfillmentSystem.Service.BbOperationsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.parsing.OrderFulfillmentSystem.Shared.sharedObjects.INSTANCE;

@Service
public class BbOperationsServiceImpl implements BbOperationsService {

    private final OrderRepo orderRepo;
    List<Order> orderList = new ArrayList<>();

    public BbOperationsServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
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


}
