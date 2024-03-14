package com.example.db;

import com.example.core.Character;

public interface CharacterDAO {

    void createCharacter(Character character);
    Character findCharacterById(int id);
    void updateCharacter(Character character);
    void deleteCharacterById(int id);
}
