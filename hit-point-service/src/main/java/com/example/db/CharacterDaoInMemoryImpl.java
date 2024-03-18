package com.example.db;

import com.example.core.Character;
import com.example.services.BrivUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class CharacterDaoInMemoryImpl implements CharacterDAO {

    @Getter
    private static final CharacterDaoInMemoryImpl instance = new CharacterDaoInMemoryImpl();

    private static final String BRIV_ID = "briv";

    private final Map<String, Character> storage;

    // Private constructor to prevent instantiation from other classes
    private CharacterDaoInMemoryImpl() {
        storage = new HashMap<>();
        storage.put(BRIV_ID, BrivUtils.getBrivData());
    }
    @Override
    // create and update are identical in this implementation
    public void create(Character character) {
        update(character);
    }

    @Override
    public Character findById(String id) {
        return storage.get(id);
    }

    @Override
    public List<Character> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(Character character) {
        storage.put(character.getName(), character);
    }

    @Override
    public void deleteById(String id) {
        storage.put(id, null);
    }
}
