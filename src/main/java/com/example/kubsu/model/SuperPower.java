package com.example.kubsu.model;

public enum SuperPower {
    FLIGHT("Летать"), THOUGHT_READING("Читать мысли"),
    ANIMAL_SPEAKING("Разговаривать с животными"), TIME_MANAGEMENT("Управлять временем");

    private final String displayValue;

    SuperPower(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
