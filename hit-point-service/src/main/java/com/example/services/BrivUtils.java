package com.example.services;

import com.example.core.Character;
import com.example.db.CharacterDaoInMemoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class BrivUtils {

    public static final int BRIV_ID = 1;

    public static Character getBrivData() {
        try {
            return new ObjectMapper().readValue(new File("src/main/resources/briv.json"), Character.class);
//            return new Character();
        } catch (Exception e) {
            System.out.println(e);
            // TODO handle exception better
            return null;
        }
    }

    private void resetStoredBriv() {
        Character briv = BrivUtils.getBrivData();
        briv.setId(BRIV_ID);
        CharacterDaoInMemoryImpl.getInstance().updateCharacter(briv);
    }
}
