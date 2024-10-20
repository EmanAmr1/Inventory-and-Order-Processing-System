package com.parsing.OrderFulfillmentSystem.Service.Impl;

import com.parsing.OrderFulfillmentSystem.Service.LoadCsvFilesService;
import com.parsing.OrderFulfillmentSystem.Service.ParseCsvFilesService;
import com.parsing.OrderFulfillmentSystem.Service.RunningService;
import org.springframework.stereotype.Service;

@Service
public class RunningServiceImpl implements RunningService {

    private final LoadCsvFilesService loadCsvFilesService;
    private final ParseCsvFilesService parseCsvFilesService;
    public RunningServiceImpl(LoadCsvFilesService loadCsvFilesService, ParseCsvFilesService parseCsvFilesService) {
        this.loadCsvFilesService = loadCsvFilesService;
        this.parseCsvFilesService = parseCsvFilesService;
    }

    @Override
    public void run(String Directory) {
        loadCsvFilesService.loadCsvFiles(Directory);

        System.out.println("start load orders");
        parseCsvFilesService.parseOrderCsvFiles();
        System.out.println("finish parse order files");

        System.out.println("start load product");
        parseCsvFilesService.parseProductCsvFiles();
        System.out.println("finish parse product files");

        System.out.println("start load Shipment");
        parseCsvFilesService.parseShipmentCsvFiles();
        System.out.println("finish parse Shipment files");

        System.out.println("start load Invoice");
        parseCsvFilesService.parseInvoiceCsvFiles();
        System.out.println("finish parse Invoice files");

    }
}
