package org.example.model.types;

public enum CallType {
    TYPE_01("01"),
    TYPE_02("02");

    private final String typeStr;

    CallType(String typeStr){
        this.typeStr = typeStr;
    }

    public String getType(){
        return typeStr;
    }

    public static CallType fromString(String text) {
        for (CallType callType : CallType.values()) {
            if (callType.typeStr.equals(text)) {
                return callType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
