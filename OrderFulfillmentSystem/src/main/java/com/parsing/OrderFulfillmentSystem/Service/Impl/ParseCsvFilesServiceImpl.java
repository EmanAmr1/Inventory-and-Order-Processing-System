package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Model.InvoiceModel;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Model.ProductModel;
import com.parsing.OrderFulfillmentSystem.Model.ShipmentModel;
import com.parsing.OrderFulfillmentSystem.Service.ParseCsvFilesService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.parsing.OrderFulfillmentSystem.Shared.sharedObjects.INSTANCE;

@Service
public class ParseCsvFilesServiceImpl implements ParseCsvFilesService {

    @Override
    public void parseOrderCsvFiles() {
        try {
            INSTANCE.isReadingOrderFiles=true;
            while (!INSTANCE.orderFiles.isEmpty()){
               processOrder(INSTANCE.orderFiles.poll());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            INSTANCE.isReadingOrderFiles=false;
        }

    }

     private void processOrder(String file){
        try {

            Path path= Path.of(file);
            CSVParser csvParser = new CSVParser(Files.newBufferedReader(path),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader());

            // Define a formatter to append time '00:00:00'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (CSVRecord record : csvParser){

                String orderDateString = record.get(4) + " 00:00:00";
                LocalDateTime orderDate = LocalDateTime.parse(orderDateString, formatter);

                if(record.size()==5){
                    OrderModel order =OrderModel
                      .builder()
                            .customerName(record.get(0))
                            .status(record.get(1))
                            .quantity(Integer.valueOf(record.get(3)))
                            .productId(Long.valueOf(record.get(2)))
                            .orderDate(orderDate)
                      .build();

                    INSTANCE.orderQueue.put(order);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

     }

///////////////////////////////////////////////////////////
    @Override
    public void parseProductCsvFiles() {

        try {
            INSTANCE.isReadingProductFiles=true;
            while (!INSTANCE.productFiles.isEmpty()){
                processProduct(INSTANCE.productFiles.poll());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            INSTANCE.isReadingProductFiles=false;
        }
    }



    private void processProduct(String file){
        try {

            CSVParser csvParser =new CSVParser(Files.newBufferedReader(Path.of(file)),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader());

            for (CSVRecord record : csvParser){
                if(record.size()==4){
                    ProductModel product = ProductModel
                            .builder()
                            .name(record.get(0))
                            .description(record.get(1))
                            .stock(Integer.valueOf(record.get(2)))
                            .price(Double.valueOf(record.get(3)))
                            .build();

                    INSTANCE.productQueue.put(product);
                }
            }


        }catch (Exception e){
            System.err.println("Failed to create path from file: " + file);
            e.printStackTrace();
            return;
        }
  }

  ////////////////////////////////////////////////////////////////////

    @Override
    public void parseShipmentCsvFiles() {
    try {
        INSTANCE.isReadingShipmentFiles=true;
        while (!INSTANCE.shipmentFiles.isEmpty()){
            processShipment(INSTANCE.shipmentFiles.poll());
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    finally {
        INSTANCE.isReadingShipmentFiles=false;
    }
    }


    private void processShipment(String file){
        try {
            Path path= Path.of(file);

            CSVParser csvParser =new CSVParser(Files.newBufferedReader(path),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader());

            // Define a formatter to append time '00:00:00'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (CSVRecord record : csvParser){

                if(record.size()==5){
                    String shipmentDateString = record.get(0) + " 00:00:00";
                    LocalDateTime shipmentDate = LocalDateTime.parse(shipmentDateString, formatter);

                    ShipmentModel shipmentModel=ShipmentModel
                            .builder()
                            .shipmentDate(shipmentDate)
                            .trackingNumber(record.get(1))
                            .status(record.get(2))
                            .carrier(record.get(3))
                            .orderId(Long.valueOf(record.get(4)))
                            .build();

                    INSTANCE.shipmentQueue.put(shipmentModel);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
  }
  /////////////////////////////////////////////////////////////////////////

    @Override
    public void parseInvoiceCsvFiles() {
        try {
            INSTANCE.isReadingInvoiceFiles=true;
            while (!INSTANCE.invoiceFiles.isEmpty()){
                processInvoice(INSTANCE.invoiceFiles.poll());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            INSTANCE.isReadingInvoiceFiles=false;
        }
    }

    private void processInvoice(String file){
        try {
            Path path= Path.of(file);

            CSVParser csvParser =new CSVParser(Files.newBufferedReader(path),
                    CSVFormat.DEFAULT.withFirstRecordAsHeader());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (CSVRecord record : csvParser){

                String invoiceDateString = record.get(0) + " 00:00:00";
                LocalDateTime invoiceDate = LocalDateTime.parse(invoiceDateString, formatter);

                if(record.size()==4){
                    InvoiceModel invoice =InvoiceModel
                            .builder()
                            .invoiceDate(invoiceDate)
                            .orderId(Long.valueOf(record.get(1)))
                            .paymentStatus(record.get(2))
                            .total(Double.valueOf(record.get(3)))
                            .build();

                    INSTANCE.invoiceQueue.put(invoice);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
