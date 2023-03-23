package org.example.service;

import org.example.model.CDRFormat;
import org.example.model.types.CallType;
import org.example.model.types.TariffType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ParseCDR {
    private final Map<String, ArrayList<CDRFormat>> cdrRepository;

    public ParseCDR(){
        cdrRepository = new HashMap<>();
    }

    public Map<String, ArrayList<CDRFormat>> convertCDRFormatToHashMap(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                CallType callType = CallType.fromString(values[0].trim());
                String phoneNumber = values[1].trim();
                LocalDateTime startTime = LocalDateTime.parse(values[2].trim(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                LocalDateTime endTime = LocalDateTime.parse(values[3].trim(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                TariffType tariffType = TariffType.fromString(values[4].trim());

                CDRFormat cdr = new CDRFormat(callType, phoneNumber, startTime, endTime, tariffType);
                ArrayList<CDRFormat> cdrList = cdrRepository.getOrDefault(phoneNumber, new ArrayList<>());
                cdrList.add(cdr);
                cdrRepository.put(phoneNumber, cdrList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, ArrayList<CDRFormat>> entry : cdrRepository.entrySet()) {
            ArrayList<CDRFormat> sortedList = entry.getValue();
            sortedList.sort(Comparator.comparing(CDRFormat::getStartCallTime));
            entry.setValue(sortedList);
        }
        return cdrRepository;
    }
}
