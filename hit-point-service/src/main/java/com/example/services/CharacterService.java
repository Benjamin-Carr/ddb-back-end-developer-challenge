package com.example.services;

import com.example.api.HitPointsResponse;
public class CharacterService {

    public HitPointsResponse stub() {
        return HitPointsResponse.builder()
                .maxHitPoints(25)
                .currentHitPoints(20)
                .currentHitPointsDelta(-5)
                .tempHitPoints(0)
                .tempHitPointsDelta(-3)
                .build();
    }

}