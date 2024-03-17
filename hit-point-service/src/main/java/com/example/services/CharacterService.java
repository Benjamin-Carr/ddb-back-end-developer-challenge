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
        Character character = characterDAO.findById(characterId);
        HitPointsResponse hitPointsResponse = HitPointCalculator.updateHitPoints(character, hitPointChange);
        characterDAO.update(character);
        return hitPointsResponse;
    }

    public HitPointsResponse addTempHp(int characterId, int tempHp) {
        Character character = characterDAO.findById(characterId);
        HitPointsResponse hitPointsResponse = TemporaryHitPointCalculator.addTemporaryHitPoints(character, tempHp);
        characterDAO.update(character);
        return hitPointsResponse;
    }
}