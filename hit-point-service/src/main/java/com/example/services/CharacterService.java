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

import static java.lang.Math.min;

public class CharacterService {

    private final CharacterDAO characterDAO;

    public CharacterService() {
        characterDAO = CharacterDaoInMemoryImpl.getInstance();
    }

    public HitPointsResponse stub() {
        return HitPointsResponse.builder()
                .maxHitPoints(25)
                .currentHitPoints(20)
                .currentHitPointsDelta(-5)
                .tempHitPoints(0)
                .tempHitPointsDelta(-3)
                .build();
    }

    public HitPointsResponse dealDamage(int characterId, Damage damage) {
        Character character = characterDAO.findCharacterById(characterId);

        HitPointsResponse hitPointsResponse = calculateDamage(character, damage);

        character.setTempHitPoints(hitPointsResponse.getTempHitPoints());
        character.setCurrentHitPoints(hitPointsResponse.getCurrentHitPoints());

        characterDAO.updateCharacter(character);

        return hitPointsResponse;
    }

    private static HitPointsResponse calculateDamage(Character character, Damage damage) {
        // adjust damage based on defenses
        float multiplier = getDamageMultiplier(character.getDefenses(), damage.getDamageType());
        int damageAmount = (int) Math.floor(damage.getAmount() * multiplier);

        // deduct temporary HP
        final int tempHpOriginal = character.getTempHitPoints();
        final int tempHpLost = min(damageAmount, tempHpOriginal);
        final int tempHpRemaining = tempHpOriginal - tempHpLost;
        damageAmount -= tempHpLost;

        // deduct regular HP
        final int hpOriginal = character.getCurrentHitPoints();
        final int hpLost = min(damageAmount, hpOriginal);
        final int hpRemaining = hpOriginal - hpLost;
        damageAmount -= hpLost;

        return HitPointsResponse.builder()
                .maxHitPoints(character.getMaxHitPoints())
                .tempHitPoints(tempHpRemaining)
                .tempHitPointsDelta(tempHpLost * -1)
                .currentHitPoints(hpRemaining)
                .currentHitPointsDelta(hpLost * -1)
                .overflow(damageAmount)
                .multiplier(multiplier)
                .build();
    }

    // TODO too many repeated words makes this hard to read
    private static float getDamageMultiplier(List<Defense> defenses, DamageType damageType) {
        return defenses.stream()
                .filter(d -> d.getDamageType().equalsIgnoreCase(damageType.getName()))
                .map(defense -> DamageMultiplier.of(defense.getDamageMultiplier()))
                .findFirst()   // TODO defend against the case of multiple/conflicting multipliers
                .orElse(DamageMultiplier.NORMAL)
                .getMultiplier();
    }
}