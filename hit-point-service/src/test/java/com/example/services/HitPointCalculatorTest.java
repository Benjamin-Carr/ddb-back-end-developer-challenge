package com.example.services;

import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.core.Defense;
import com.example.core.enums.DamageMultiplier;
import com.example.core.enums.DamageType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.example.core.enums.DamageMultiplier.*;
import static com.example.core.enums.DamageType.*;
import static com.example.services.HitPointCalculator.updateHitPoints;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("HitPointCalculator")
class HitPointCalculatorTest {

    @DisplayName("should")
    @ParameterizedTest(name = "{0}")
    @CsvSource({
            "increase HP when healing, 5, 15",
            "reduce HP when dealing damage, -5, 5",
            "not change HP when healing or damage is zero, 0, 10",
    })
    void happyPathTest(String testName, int hitPointChangeAmount, int expectedHitPoints) {
        int maxHitPoints = 25;
        int currentHitPoints = 10;
        int tempHitPoints = 0; // also used for expectedTempHitPoints
        int expectedOverflow = 0;
        updateHitPointsTest(hitPointChangeAmount, maxHitPoints, currentHitPoints, tempHitPoints, expectedHitPoints, tempHitPoints, expectedOverflow);
    }

    @Nested
    class should_handle_overflows {

        @DisplayName("HP should not")
        @ParameterizedTest(name = "{0}")
        @CsvSource({
                "go below zero, -100, 0, 0, -70",
                "go above max, 100, 25, 5, 100",
        })
        void overflowTest(String testName, int hitPointChangeAmount, int expectedHitPoints, int expectedTempHitPoints, int expectedOverflow) {
            int maxHitPoints = 25;
            int tempHitPoints = 5;
            updateHitPointsTest(hitPointChangeAmount, maxHitPoints, maxHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
        }
    }

    @Nested
    class should_handle_defenses {

        @DisplayName("damage should")
        @ParameterizedTest(name = "{0} {1}")
        @CsvSource({
                "be prevented by, immunity, 50, 5", // damage goes to zero, HP does not change
                "be halved by, resistance, 45, 0", // damage halves to 10, 5 go to temp, 5 to regular
                "be doubled by, vulnerability, 15, 0" // damage doubles to 40, 5 go to temp, 35 to regular
        })
        void defensesTest(String testName, String damageMultiplier, int expectedHitPoints, int expectedTempHitPoints) {
            Defense defense = new Defense(DamageMultiplier.of(damageMultiplier), LIGHTNING);
            HitPointChange damage = new HitPointChange(-20, LIGHTNING);
            int maxHitPoints = 50;
            int currentHitPoints = 50;
            int tempHitPoints = 5;
            int expectedOverflow = 0;
            updateHitPointsTest(defense, damage, maxHitPoints, currentHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
        }

        @Test
        void defenses_must_match_damage_type_to_take_effect() {
            Defense defense = new Defense(IMMUNITY, COLD);
            HitPointChange damage = new HitPointChange(-20, FIRE);
            int maxHitPoints = 50; // also current hit points
            int tempHitPoints = 5;
            // 5 damage go to temp HP, the other 15 to regular HP
            final int expectedHitPoints = 35;
            int expectedTempHitPoints = 0;
            int expectedOverflow = 0;

            updateHitPointsTest(defense, damage, maxHitPoints, maxHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
        }
    }

    @Nested
    class should_handle_temporary_HP {

        @Test
        void damage_should_deduct_temporary_HP_before_regular_HP() {
            int maxHitPoints = 25;
            int currentHitPoints = 23;
            int tempHitPoints = 5;
            int hitPointChangeAmount = -3;
            int expectedHitPoints = 23;
            int expectedTempHitPoints = 2;
            int expectedOverflow = 0;
            updateHitPointsTest(hitPointChangeAmount, maxHitPoints, currentHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
        }

        @Test
        void healing_should_not_affect_temporary_HP() {
            int maxHitPoints = 25;
            int currentHitPoints = 23;
            int tempHitPoints = 5;
            int hitPointChangeAmount = 10;
            int expectedHitPoints = 25;
            int expectedTempHitPoints = 5;
            int expectedOverflow = 8;
            updateHitPointsTest(hitPointChangeAmount, maxHitPoints, currentHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
        }
    }

    void updateHitPointsTest(int changeAmount, int maxHitPoints, int currentHitPoints, int tempHitPoints, int expectedHitPoints, int expectedTempHitPoints, int expectedOverflow) {
        // only give a damage type to attacks, healing doesn't need one
        DamageType damageType = changeAmount < 0 ? SLASHING : null;
        updateHitPointsTest(null, new HitPointChange(changeAmount, damageType), maxHitPoints, currentHitPoints, tempHitPoints, expectedHitPoints, expectedTempHitPoints, expectedOverflow);
    }

    void updateHitPointsTest(Defense defense, HitPointChange damage, int maxHitPoints, int currentHitPoints, int tempHitPoints, int expectedHitPoints, int expectedTempHitPoints, int expectedOverflow) {
        Character character = generateCharacter(maxHitPoints, currentHitPoints, tempHitPoints);
        if (defense != null) {
            character.setDefenses(List.of(defense));
        }

        HitPointsResponse data = updateHitPoints(character, damage);

        // verify changes to character
        assertThat(character.getCurrentHitPoints(), is(expectedHitPoints));
        assertThat(character.getTempHitPoints(), is(expectedTempHitPoints));

        // verify outgoing data is accurate
        assertThat(data.getMaxHitPoints(), is(equalTo(maxHitPoints)));
        assertThat(data.getCurrentHitPoints(), is(expectedHitPoints));
        assertThat(data.getCurrentHitPointsDelta(), is(expectedHitPoints - currentHitPoints));
        assertThat(data.getTempHitPoints(), is(expectedTempHitPoints));
        assertThat(data.getTempHitPointsDelta(), is(equalTo(expectedTempHitPoints - tempHitPoints)));
        assertThat(data.getOverflow(), is(expectedOverflow));
    }

    private Character generateCharacter(int maxHitPoints, int currentHitPoints, int temporaryHitPoints) {
        return Character.builder()
                .maxHitPoints(maxHitPoints)
                .currentHitPoints(currentHitPoints)
                .tempHitPoints(temporaryHitPoints)
                .build();
    }
}
