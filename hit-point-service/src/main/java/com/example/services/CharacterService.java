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
        HitPointsResponse hitPointsResponse = dealDamage(character, damage);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    public HitPointsResponse heal(int characterId, int healingAmount) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = heal(character, healingAmount);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    public HitPointsResponse addTempHp(int characterId, int tempHp) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = addTempHp(character, tempHp);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    private static HitPointsResponse dealDamage(Character character, Damage damage) {
        // adjust damage based on defenses
        float multiplier = getDamageMultiplier(character.getDefenses(), damage.getDamageType());
        int damageAmount = (int) Math.floor(damage.getAmount() * multiplier);

        // deduct temporary HP
        final int tempHpLost = min(damageAmount, character.getTempHitPoints());
        final int tempHpRemaining = character.getTempHitPoints() - tempHpLost;
        damageAmount -= tempHpLost;

        // deduct regular HP
        final int hpLost = min(damageAmount, character.getCurrentHitPoints());
        final int hpRemaining = character.getCurrentHitPoints() - hpLost;
        damageAmount -= hpLost;

        // update character model
        character.setTempHitPoints(tempHpRemaining);
        character.setCurrentHitPoints(hpRemaining);

        return HitPointsResponse.of(character).toBuilder()
                .tempHitPointsDelta(tempHpLost * -1)
                .currentHitPointsDelta(hpLost * -1)
                .overflow(damageAmount)
                .multiplier(multiplier)
                .build();
    }

    private static HitPointsResponse heal(Character character, int healingAmount) {
        // add regular HP, without going over max
        final int hpHealed = min(healingAmount, character.getMaxHitPoints() - character.getCurrentHitPoints());
        final int hpNew = character.getCurrentHitPoints() + hpHealed;
        healingAmount -= hpHealed;

        character.setCurrentHitPoints(hpNew);

        return HitPointsResponse.of(character).toBuilder()
                .currentHitPointsDelta(hpHealed)
                .overflow(healingAmount)
                .build();
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