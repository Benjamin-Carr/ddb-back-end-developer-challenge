package com.example.services;

import com.example.core.Character;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class BrivUtils {

    private static final Logger logger = LoggerFactory.getLogger(BrivUtils.class);

    public static Character getBrivData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
            Character briv = objectMapper.readValue(new File("src/main/resources/briv.json"), Character.class);
            // JSON doesn't come with current hit points, so we have to add them in
            briv.setCurrentHitPoints(briv.getMaxHitPoints());
            return briv;
        } catch (Exception e) {
            logger.error(e.getMessage());
            // TODO handle exception better
            return null;
        }
    }
}
