package com.example.services;

import com.example.api.Damage;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.db.CharacterDAO;
import com.example.db.CharacterDaoInMemoryImpl;

import static java.lang.Math.min;

public class CharacterService {

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
        CharacterDAO characterDAO = CharacterDaoInMemoryImpl.getInstance();
        Character character = characterDAO.findCharacterById(characterId);

        HitPointsResponse hitPointsResponse = calculateDamage(character, damage);

        character.setTempHitPoints(hitPointsResponse.getTempHitPoints());
        character.setCurrentHitPoints(hitPointsResponse.getCurrentHitPoints());

        characterDAO.updateCharacter(character);

        return hitPointsResponse;
    }

    private static HitPointsResponse calculateDamage(Character character, Damage damage) {
        int damageAmount = damage.getAmount();

        // deal with temporary HP
        final int tempHpOriginal = character.getTempHitPoints();
        final int tempHpLost = min(damageAmount, tempHpOriginal);
        final int tempHpRemaining = tempHpOriginal - tempHpLost;
        damageAmount -= tempHpLost;

        // deal with regular HP
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
                .build();
    }

}