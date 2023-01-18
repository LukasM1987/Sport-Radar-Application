package com.event.sportradar.Service;

public enum SkirmishResult {

    WIN (0),
    DRAW (1);

    private int value;

    SkirmishResult(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}