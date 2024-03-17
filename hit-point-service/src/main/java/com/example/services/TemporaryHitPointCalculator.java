package com.example.services;

import com.example.api.HitPointsResponse;
import com.example.core.Character;

public class TemporaryHitPointCalculator {
    public static HitPointsResponse addTemporaryHitPoints(Character character, int tempHp) {
        // don't allow negative temp HP
        if (tempHp < 0) {
            return null;
        }

        // don't use new temp HP value unless it's higher than the existing value
        tempHp = Math.max(tempHp, character.getTempHitPoints());
        int tempHpDelta = tempHp - character.getTempHitPoints();

        character.setTempHitPoints(tempHp);

        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHpDelta)
                .build();
    }

    public static HitPointsResponse removeTemporaryHitPoints(Character character) {
        int tempHiPointsDelta = character.getTempHitPoints() * -1;

        character.setTempHitPoints(0);

        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHiPointsDelta)
                .build();
    }
}
