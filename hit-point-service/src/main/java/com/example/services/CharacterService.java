package com.example.services;

import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.db.CharacterDAO;
import com.example.db.CharacterDaoInMemoryImpl;

public class CharacterService {

    private final CharacterDAO characterDAO;

    public CharacterService() {
        characterDAO = CharacterDaoInMemoryImpl.getInstance();
    }

    public HitPointsResponse updateHitPoints(int characterId, HitPointChange hitPointChange) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = HitPointCalculator.updateHitPoints(character, hitPointChange);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
    }

    public HitPointsResponse addTempHp(int characterId, int tempHp) {
        Character character = characterDAO.findCharacterById(characterId);
        HitPointsResponse hitPointsResponse = addTempHp(character, tempHp);
        characterDAO.updateCharacter(character);
        return hitPointsResponse;
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