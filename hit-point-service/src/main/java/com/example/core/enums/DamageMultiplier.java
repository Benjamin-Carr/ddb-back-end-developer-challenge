package com.example.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum DamageMultiplier {
    VULNERABILITY("vulnerability", 2f),
    NORMAL("normal", 1f),
    RESISTANCE("resistance", 0.5f),
    IMMUNITY("immunity", 0f);

    DamageMultiplier(String name, float multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    private final String name;
    private final float multiplier;

    private static final Map<String, DamageMultiplier> map = new HashMap<>();

    static {
        for (DamageMultiplier d : values()) {
            map.put(d.name, d);
        }
    }

    public static DamageMultiplier of(String name) {
        DamageMultiplier result = map.get(name);
        if (result == null) {
            throw new IllegalArgumentException("Invalid category name: " + name);
        }
        return result;
    }
}
