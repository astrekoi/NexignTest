package org.example.model.types;

public enum TariffType {
    TYPE_06("06"),
    TYPE_03("03"),
    TYPE_11("11");

    private final String tariffStr;

    TariffType(String tariffStr){
        this.tariffStr = tariffStr;
    }

    public String getType(){
        return tariffStr;
    }

    public static TariffType fromString(String text) {
        for (TariffType tariffType : TariffType.values()) {
            if (tariffType.tariffStr.equals(text)) {
                return tariffType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}