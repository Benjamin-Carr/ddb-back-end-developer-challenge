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
    int tempHitPointsDelta;
    int currentHitPoints;
    int currentHitPointsDelta;
    float multiplier;
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