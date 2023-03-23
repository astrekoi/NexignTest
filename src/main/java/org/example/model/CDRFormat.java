package org.example.model;

import org.example.model.types.CallType;
import org.example.model.types.TariffType;

import java.time.LocalDateTime;

public class CDRFormat {

    private String phoneNumber;
    private CallType callType;
    private LocalDateTime startCallTime;
    private LocalDateTime endCallTime;
    private TariffType tariffType;

    public CDRFormat() {
    }

    public CDRFormat(String callType, String phoneNumber, LocalDateTime startCallTime, LocalDateTime endCallTime, String tariffType) {
        this.callType = CallType.valueOf("TYPE_" + callType);
        this.phoneNumber = phoneNumber;
        this.startCallTime = startCallTime;
        this.endCallTime = endCallTime;
        this.tariffType = TariffType.valueOf("TYPE_" + tariffType);
    }

    public CDRFormat(CallType callType, String phoneNumber, LocalDateTime startCallTime, LocalDateTime endCallTime, TariffType tariffType) {
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.startCallTime = startCallTime;
        this.endCallTime = endCallTime;
        this.tariffType = tariffType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public LocalDateTime getStartCallTime() {
        return startCallTime;
    }

    public void setStartCallTime(LocalDateTime startCallTime) {
        this.startCallTime = startCallTime;
    }

    public LocalDateTime getEndCallTime() {
        return endCallTime;
    }

    public void setEndCallTime(LocalDateTime endCallTime) {
        this.endCallTime = endCallTime;
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }
}
