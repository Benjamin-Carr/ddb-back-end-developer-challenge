package com.example.services;

import com.example.api.Damage;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.core.Defense;
import com.example.core.enums.DamageMultiplier;
import com.example.core.enums.DamageType;
import com.example.db.CharacterDAO;
import com.example.db.CharacterDaoInMemoryImpl;
import java.util.List;

import static java.lang.Math.*;

public class CharacterService {

    private final CharacterDAO characterDAO;

    public CharacterService() {
        characterDAO = CharacterDaoInMemoryImpl.getInstance();
    }

    public HitPointsResponse updateHitPoints(int characterId, Damage damage) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = updateHitPoints(character, damage);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    public HitPointsResponse addTempHp(int characterId, int tempHp) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = addTempHp(character, tempHp);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    private static HitPointsResponse updateHitPoints(Character character, Damage damage) {
        // adjust damage based on defenses
        float multiplier = getMultiplier(character, damage);
        int delta = (int) floor(damage.getAmount() * multiplier);

        return updateHitPoints(character, delta).toBuilder()
                .multiplier(multiplier)
                .build();
    }

    private static HitPointsResponse updateHitPoints(Character character, int delta) {
        // adjust temporary HP
        final int tempHpDelta = getTempHitPointsDelta(character.getTempHitPoints(), delta);
        final int tempHpRemaining = character.getTempHitPoints() + tempHpDelta;
        delta -= tempHpDelta;

        // adjust regular HP second
        final int currentHpDelta = getHitPointsDelta(character.getCurrentHitPoints(), character.getMaxHitPoints(), delta);
        final int hpNew = character.getCurrentHitPoints() + currentHpDelta;
        delta -= currentHpDelta;

        // update character model
        character.setCurrentHitPoints(hpNew);
        character.setTempHitPoints(tempHpRemaining);

        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHpDelta)
                .currentHitPointsDelta(currentHpDelta)
                .overflow(delta)
                .multiplier(1)
                .build();
    }

    private static float getMultiplier(Character character, Damage damage) {
        return damage.getAmount() < 0
                ? getDamageMultiplier(character.getDefenses(), damage.getDamageType())
                // no multipliers applied to healing
                : DamageMultiplier.NORMAL.getMultiplier();
    }

    // TODO too many repeated words makes this hard to read
    private static float getDamageMultiplier(List<Defense> defenses, DamageType damageType) {
        if (defenses == null) {
            return DamageMultiplier.NORMAL.getMultiplier();
        }
        return defenses.stream()
                .filter(d -> d.getDamageType().equalsIgnoreCase(damageType.getName()))
                .map(defense -> DamageMultiplier.of(defense.getDamageMultiplier()))
                .findFirst()   // TODO defend against the case of multiple/conflicting multipliers
                .orElse(DamageMultiplier.NORMAL)
                .getMultiplier();
    }

    private static int getTempHitPointsDelta(int tempHitPoints, int delta) {
        return delta < 0
                // can't decrease more than total temp HP
                ? min(abs(delta), tempHitPoints) * -1
                // don't alter temp HP when healing
                : 0;
    }

    private static int getHitPointsDelta(int currentHitPoints, int maxHitPoints, int delta) {
        return delta < 0
                // can't decrease more than current HP
                ? min(abs(delta), currentHitPoints) * -1
                // can't heal more HP than character has lost
                : min(delta, maxHitPoints - currentHitPoints);
    }

    private static HitPointsResponse addTempHp(Character character, int tempHp) {
        // TODO don't allow negative temp HP
        // don't use new temp HP value unless it's higher than the existing value
        tempHp = Math.max(tempHp, character.getTempHitPoints());
        int tempHpDelta = tempHp - character.getTempHitPoints();

        character.setTempHitPoints(tempHp);

        // TODO should it count as overflow if the new temp HP value isn't used?
        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHpDelta)
                .build();
    }
}