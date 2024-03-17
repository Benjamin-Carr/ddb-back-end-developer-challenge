package com.example.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum DamageType {
    BLUDGEONING("Bludgeoning"),
    PIERCING("Piercing"),
    SLASHING("Slashing"),
    FIRE("Fire"),
    COLD("Cold"),
    ACID("Acid"),
    THUNDER("Thunder"),
    LIGHTNING("Lightning"),
    POISON("Poison"),
    RADIANT("Radiant"),
    NECROTIC("Necrotic"),
    PSYCHIC("Psychic"),
    FORCE("Force");

    private final String name;

    DamageType(String name) {
        this.name = name;
    }

    public String toString() {
        return getName();
    }

    private static final Map<String, DamageType> map = new HashMap<>();

    static {
        for (DamageType d : values()) {
            map.put(d.name.toLowerCase(), d);
        }
    }

    public static DamageType of(String name) {
        DamageType result = map.get(name.toLowerCase());
        if (result == null) {
            throw new IllegalArgumentException("Invalid DamageType name: " + name);
        }
        return result;
    }
}