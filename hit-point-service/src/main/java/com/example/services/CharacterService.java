package com.example.services;

import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.db.CharacterDAO;
import com.example.db.CharacterDaoInMemoryImpl;

import java.util.List;

public class CharacterService {

    private final CharacterDAO characterDAO;

    public CharacterService() {
        characterDAO = CharacterDaoInMemoryImpl.getInstance();
    }

    public HitPointsResponse updateHitPoints(String characterId, HitPointChange hitPointChange) {
        Character character = characterDAO.findById(characterId);
        HitPointsResponse hitPointsResponse = HitPointCalculator.updateHitPoints(character, hitPointChange);
        characterDAO.update(character);
        return hitPointsResponse;
    }

    public HitPointsResponse addTempHp(String characterId, int tempHp) {
        Character character = characterDAO.findById(characterId);
        HitPointsResponse hitPointsResponse = TemporaryHitPointCalculator.addTemporaryHitPoints(character, tempHp);
        characterDAO.update(character);
        return hitPointsResponse;
    }

    public HitPointsResponse removeTempHp(String characterId) {
        Character character = characterDAO.findById(characterId);
        HitPointsResponse hitPointsResponse = TemporaryHitPointCalculator.removeTemporaryHitPoints(character);
        characterDAO.update(character);
        return hitPointsResponse;
    }

    public Character getCharacter(String characterId) {
        return characterDAO.findById(characterId);
    }

    public List<Character> getAllCharacters() {
        return characterDAO.findAll();
    }
}