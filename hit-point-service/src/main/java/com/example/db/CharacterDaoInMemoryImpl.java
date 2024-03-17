package com.example.db;

import com.example.core.Character;
import com.example.services.BrivUtils;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class CharacterDaoInMemoryImpl implements CharacterDAO {

    @Getter
    private static final CharacterDaoInMemoryImpl instance = new CharacterDaoInMemoryImpl();

    private static final int BRIV_ID = 1;

    private final Map<Integer, Character> storage;

    // Private constructor to prevent instantiation from other classes
    private CharacterDaoInMemoryImpl() {
        storage = new HashMap<>();
        try {
            Character briv = BrivUtils.getBrivData();
            briv.setId(BrivUtils.BRIV_ID);
            storage.put(BRIV_ID, briv);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    // create and update are identical in this implementation
    public void createCharacter(Character character) {
        updateCharacter(character);
    }

    @Override
    public Character findCharacterById(int id) {
        return storage.get(id);
    }

    @Override
    public void updateCharacter(Character character) {
        storage.put(character.getId(), character);
    }

    @Override
    public void deleteCharacterById(int id) {
        storage.put(id, null);
    }
}
