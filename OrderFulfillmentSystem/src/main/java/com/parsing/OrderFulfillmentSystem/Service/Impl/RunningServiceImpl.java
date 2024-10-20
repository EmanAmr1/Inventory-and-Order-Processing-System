package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Service.DBOperationsService;
import com.parsing.OrderFulfillmentSystem.Service.LoadCsvFilesService;
import com.parsing.OrderFulfillmentSystem.Service.ParseCsvFilesService;
import com.parsing.OrderFulfillmentSystem.Service.RunningService;
import org.springframework.stereotype.Service;

@Service
public class RunningServiceImpl implements RunningService {

    private final LoadCsvFilesService loadCsvFilesService;
    private final ParseCsvFilesService parseCsvFilesService;
    private final DBOperationsService dbOperationsService;
    public RunningServiceImpl(LoadCsvFilesService loadCsvFilesService, ParseCsvFilesService parseCsvFilesService, DBOperationsService dbOperationsService) {
        this.loadCsvFilesService = loadCsvFilesService;
        this.parseCsvFilesService = parseCsvFilesService;
        this.dbOperationsService = dbOperationsService;
    }

    @Override
    public void run(String Directory) {

        System.out.println("start running project");

        System.out.println("start loading Csv Files");
        loadCsvFilesService.loadCsvFiles(Directory);
        System.out.println("finish loading Csv Files");

        System.out.println("start load orders");
        parseCsvFilesService.parseOrderCsvFiles();
        dbOperationsService.addOrder();
        System.out.println("finish parse order files");

        System.out.println("start load product");
        parseCsvFilesService.parseProductCsvFiles();
        dbOperationsService.addProduct();
        System.out.println("finish parse product files");

        System.out.println("start load Shipment");
        parseCsvFilesService.parseShipmentCsvFiles();
        dbOperationsService.addShipment();
        System.out.println("finish parse Shipment files");

        System.out.println("start load Invoice");
        parseCsvFilesService.parseInvoiceCsvFiles();
        dbOperationsService.addInvoice();
        System.out.println("finish parse Invoice files");

        System.out.println("finish running project");

    }
}
