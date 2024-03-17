package com.example.services;

import com.example.api.HitPointsResponse;
import com.example.core.Character;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.services.TemporaryHitPointCalculator.addTemporaryHitPoints;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("TemporaryHitPointCalculator")
public class TemporaryHitPointCalculatorTest {

    @Test
    void should_not_allow_negative_temp_HP() {
        assertNull(addTemporaryHitPoints(new Character(), -1));
    }

    @Test
    void should_add_temp_HP() {
        HitPointsResponse hitPointsResponse = addTemporaryHitPoints(new Character(), 5);
        assertNotNull(hitPointsResponse);
        assertThat(hitPointsResponse.getTempHitPoints(), is(5));
        assertThat(hitPointsResponse.getTempHitPointsDelta(), is(5));
    }

    @Test
    void should_replace_existing_temp_HP_if_new_value_is_higher() {
        Character character = new Character();
        addTemporaryHitPoints(character, 5);
        HitPointsResponse hitPointsResponse = addTemporaryHitPoints(character, 10);
        assertNotNull(hitPointsResponse);
        assertThat(hitPointsResponse.getTempHitPoints(), is(10));
        assertThat(hitPointsResponse.getTempHitPointsDelta(), is(5));
    }

    @Test
    void should_not_replace_existing_temp_HP_if_new_value_is_lower() {
        Character character = new Character();
        addTemporaryHitPoints(character, 5);
        HitPointsResponse hitPointsResponse = addTemporaryHitPoints(character, 2);
        assertNotNull(hitPointsResponse);
        assertThat(hitPointsResponse.getTempHitPoints(), is(5));
        assertThat(hitPointsResponse.getTempHitPointsDelta(), is(0));
    }
}
