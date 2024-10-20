package com.parsing.OrderFulfillmentSystem.Service;

public interface ParseCsvFilesService {
    public void parseOrderCsvFiles();
    public void parseProductCsvFiles();
    public void parseShipmentCsvFiles();
    public void parseInvoiceCsvFiles();
}
