package com.example.services;

import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.core.Defense;
import com.example.core.enums.DamageMultiplier;
import com.example.core.enums.DamageType;

import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.min;

public class HitPointCalculator {

    public static HitPointsResponse updateHitPoints(Character character, HitPointChange hitPointChange) {
        // adjust damage based on defenses
        float multiplier = getMultiplier(character, hitPointChange);
        int delta = (int) floor(hitPointChange.getAmount() * multiplier);

        // adjust temporary HP
        final int tempHpDelta = getBoundedTempHitPointsDelta(character.getTempHitPoints(), delta);
        final int tempHpRemaining = character.getTempHitPoints() + tempHpDelta;
        delta -= tempHpDelta;

        // adjust regular HP second
        final int currentHpDelta = getBoundedHitPointsDelta(character.getCurrentHitPoints(), character.getMaxHitPoints(), delta);
        final int hpNew = character.getCurrentHitPoints() + currentHpDelta;
        delta -= currentHpDelta;

        // update character model
        character.setCurrentHitPoints(hpNew);
        character.setTempHitPoints(tempHpRemaining);

        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHpDelta)
                .currentHitPointsDelta(currentHpDelta)
                .overflow(delta)
                .multiplier(multiplier)
                .build();
    }

    private static float getMultiplier(Character character, HitPointChange hitPointChange) {
        return hitPointChange.getAmount() < 0
                ? getDamageMultiplier(character.getDefenses(), hitPointChange.getDamageType())
                // no multipliers applied to healing
                : DamageMultiplier.NORMAL.getMultiplier();
    }

    // TODO too many repeated words makes this hard to read
    private static float getDamageMultiplier(List<Defense> defenses, DamageType damageType) {
        if (defenses == null) {
            return DamageMultiplier.NORMAL.getMultiplier();
        }
        return defenses.stream()
                .filter(d -> d.getDamageType().equals(damageType))
                .map(Defense::getDamageMultiplier)
                .findFirst()   // TODO defend against the case of multiple/conflicting multipliers
                .orElse(DamageMultiplier.NORMAL)
                .getMultiplier();
    }

    private static int getBoundedTempHitPointsDelta(int tempHitPoints, int delta) {
        return delta < 0
                // can't decrease more than total temp HP
                ? min(abs(delta), tempHitPoints) * -1
                // don't alter temp HP when healing
                : 0;
    }

    private static int getBoundedHitPointsDelta(int currentHitPoints, int maxHitPoints, int delta) {
        return delta < 0
                // can't decrease more than current HP
                ? min(abs(delta), currentHitPoints) * -1
                // can't heal more HP than character has lost
                : min(delta, maxHitPoints - currentHitPoints);
    }
}
