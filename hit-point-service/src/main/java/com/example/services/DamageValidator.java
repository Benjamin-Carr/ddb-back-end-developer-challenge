package com.example.services;

import com.example.api.HitPointChange;

public class DamageValidator {

    public static boolean isValid(HitPointChange input) {
        return input.getAmount() >= 0 || input.getDamageType() != null;
    }
}
