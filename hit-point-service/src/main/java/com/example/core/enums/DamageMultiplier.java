package com.example.core.enums;

import lombok.Getter;

@Getter
public enum DamageMultiplier {
    VULNERABILITY("vulnerability", 2f),
    NORMAL("normal", 1f),
    RESISTANCE("resistance", 0.5f),
    IMMUNITY("immunity", 0f);

    DamageMultiplier(String name, float value) {
        this.name = name;
        this.value = value;
    }

    private final String name;
    private final float value;
}
