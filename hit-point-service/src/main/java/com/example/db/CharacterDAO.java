package com.example.db;

import com.example.core.Character;

import java.util.List;

public interface CharacterDAO {

    void create(Character character);
    Character findById(String id);
    List<Character> findAll();
    void update(Character character);
    void deleteById(String id);
}
