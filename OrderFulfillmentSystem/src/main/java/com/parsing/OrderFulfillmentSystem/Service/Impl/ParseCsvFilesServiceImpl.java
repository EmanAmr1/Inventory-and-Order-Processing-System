package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Entity.Order;
import com.parsing.OrderFulfillmentSystem.Model.OrderModel;
import com.parsing.OrderFulfillmentSystem.Service.ParseCsvFilesService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

            boolean header=false;
            for (CSVRecord record : csvParser){

                if(!header) {
                    header=true;
                    continue;
                }

                if(record.size()==5){
                    OrderModel order =OrderModel
                      .builder()
                            .customerName(record.get(0))
                            .status(record.get(1))
                            .quantity(Integer.valueOf(record.get(3)))
                            .productId(Long.valueOf(record.get(4)))
                            .orderDate(LocalDateTime.parse(record.get(5)))
                      .build();

                    INSTANCE.orderQueue.put(order);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

     }





}
