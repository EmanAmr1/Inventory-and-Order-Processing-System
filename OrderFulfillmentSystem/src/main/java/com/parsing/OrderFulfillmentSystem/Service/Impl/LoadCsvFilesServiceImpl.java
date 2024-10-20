package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Service.LoadCsvFilesService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static com.parsing.OrderFulfillmentSystem.Shared.sharedObjects.INSTANCE;

@Service
public class LoadCsvFilesServiceImpl implements LoadCsvFilesService {

    @Override
    public void loadCsvFiles(String Directory) {
        try{
            Files.walkFileTree(Paths.get(Directory) , new SimpleFileVisitor<Path>() {

                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    String fileName = file.getFileName().toString();

                    if(fileName.endsWith(".csv")){

                        if(fileName.toLowerCase().contains("order")) INSTANCE.orderFiles.add(fileName);
                        else if(fileName.toLowerCase().contains("product")) INSTANCE.productFiles.add(fileName);
                        else if(fileName.toLowerCase().contains(("shipment"))) INSTANCE.shipmentFiles.add(fileName);
                        else if(fileName.toLowerCase().contains(("invoice"))) INSTANCE.invoiceFiles.add(fileName);
                    }
                    return FileVisitResult.CONTINUE;
                }
                   
            });
        }catch (Exception e){
            System.out.println("Error while loading files: " + e.getMessage());
        }
    }
}
