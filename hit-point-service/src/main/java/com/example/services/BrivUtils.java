package com.example.services;

import com.example.core.Character;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class BrivUtils {

    private static final Logger logger = LoggerFactory.getLogger(BrivUtils.class);

    public static final int BRIV_ID = 1;

    public static Character getBrivData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            return objectMapper.readValue(new File("src/main/resources/briv.json"), Character.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            // TODO handle exception better
            return null;
        }
    }
}
