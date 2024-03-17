package com.example.services;

import com.example.api.HitPointChange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.core.enums.DamageType.SLASHING;
import static com.example.services.DamageValidator.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DamageValidator")
class DamageValidatorTest {

    @Test
    void requires_damage_to_have_type() {
        assertFalse(isValid(new HitPointChange(-1, null)));
        assertTrue(isValid(new HitPointChange(-1, SLASHING)));

    }

    @Test
    void does_not_require_healing_to_have_type() {
        assertTrue(isValid(new HitPointChange(1, null)));
        assertTrue(isValid(new HitPointChange(1, SLASHING)));

    }
}
