package com.example.api;

import com.example.core.Character;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class HitPointsResponse {
    int maxHitPoints;
    int tempHitPoints;
    /**
     * The amount the temporary hit points changed
     */
    int tempHitPointsDelta;
    int currentHitPoints;
    /**
     * The amount the current hit points changed
     */
    int currentHitPointsDelta;
    /**
     * Factor by which the input amount was adjusted before affecting the character.
     * Usually this will be 1, but if a character is resistant to the damage type, it will be 0.5, for example.
     */
    float multiplier;
    /**
     * Number of hit points in excess of the change, either positive or negative.
     * For example, if a character is dealt more damage than they have hit points, this field will contain the remainder.
     * Likewise for if a character is healed more than they were missing hit points.
     */
    int overflow;

    public static HitPointsResponse of(Character character) {
        return HitPointsResponse.builder()
                .maxHitPoints(character.getMaxHitPoints())
                .tempHitPoints(character.getTempHitPoints())
                .currentHitPoints(character.getCurrentHitPoints())
                .multiplier(1)
                .build();
    }
}