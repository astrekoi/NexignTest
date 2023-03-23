package org.example.service;

import org.example.model.CDRFormat;
import org.example.model.types.CallType;
import org.example.model.types.TariffType;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class ReportService {

    private static final String HEADER =
                    "Tariff index: %s\n" +
                    "----------------------------------------------------------------------------\n" +
                    "Report for phone number %s:\n" +
                    "----------------------------------------------------------------------------\n" +
                    "| Call Type |      Start Time      |        End Time      | Duration | Cost  |\n" +
                    "----------------------------------------------------------------------------\n";

    private static final String FOOTER =
                    "----------------------------------------------------------------------------\n" +
                    "|                                               Total Cost: |  %.2f rubles |\n" +
                    "----------------------------------------------------------------------------\n";

    public ReportService(){}

    public void createReports(Map<String, ArrayList<CDRFormat>> cdrData) {
        for (Map.Entry<String, ArrayList<CDRFormat>> entry : cdrData.entrySet()) {
            String phoneNumber = entry.getKey();
            ArrayList<CDRFormat> cdrList = entry.getValue();
            File reportFile = new File("src/main/java/org/example/reports/" + phoneNumber + ".txt");
            try (PrintWriter pw = new PrintWriter(reportFile)) {
                pw.printf(HEADER, entry.getValue().get(0).getTariffType().getType(), phoneNumber);
                double totalCost = 0.0;
                long totalDurationMinutes = 0;
                for (CDRFormat cdr : cdrList) {
                    String callType = cdr.getCallType().getType();
                    String startTime = cdr.getStartCallTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String endTime = cdr.getEndCallTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Duration duration = Duration.between(cdr.getStartCallTime(), cdr.getEndCallTime());
                    long minutes = duration.toMinutes();
                    long seconds = duration.minusMinutes(minutes).getSeconds();
                    totalDurationMinutes += minutes;
                    String durationStr = String.format("%02d:%02d", minutes, seconds);
                    double cost = calculateCost(cdr, totalDurationMinutes);
                    totalCost += cost;
                    pw.printf("| %10s | %19s | %19s | %8s | %6.2f |\n", callType, startTime, endTime, durationStr, cost);
                }
                pw.printf(FOOTER, totalCost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public double calculateCost(CDRFormat cdr, long totalDurationMinutes) {
        double cost = 0.0;
        TariffType tariffType = cdr.getTariffType();
        CallType callType = cdr.getCallType();
        Duration duration = Duration.between(cdr.getStartCallTime(), cdr.getEndCallTime());
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();
        if (seconds > 30 || (minutes == 0 && seconds > 0)) {
            minutes += 1;
        }

        switch (tariffType) {
            case TYPE_06:
                if (totalDurationMinutes > 300) {
                    cost = minutes;
                } else {
                    cost = 0;
                }
                break;
            case TYPE_03:
                cost = minutes * 1.5;
                break;
            case TYPE_11:
                if (callType == CallType.TYPE_02) {
                    cost = 0;
                } else {
                    if (totalDurationMinutes <= 100) {
                        cost = minutes * 0.5;
                    } else {
                        cost = minutes * 1.5;
                    }
                }
                break;
        }
        return cost;
    }
}
