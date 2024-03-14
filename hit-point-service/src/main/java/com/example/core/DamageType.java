package com.example.core;

import lombok.Getter;
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

    private final String value;

    private DamageType(String value) {
        this.value = value;
    }

    public String toString() {
        return getValue();
    }
}