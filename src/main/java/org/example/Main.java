package org.example;

import org.example.service.ParseCDR;
import org.example.service.ReportService;

import java.io.File;

public class Main {

    private static final ParseCDR parseCDR = new ParseCDR();
    private static final ReportService reportService = new ReportService();

    public static void main(String[] args) {
        String relativePath = "src/main/java/org/example/input/data/cdr.txt";
        File file = new File(relativePath);
        var cdrMap = parseCDR.convertCDRFormatToHashMap(file);
        reportService.createReports(cdrMap);
    }
}