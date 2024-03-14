package com.example.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class HitPointsResponse {
    int maxHitPoints;
    int tempHitPoints;
    int tempHitPointsDelta;
    int currentHitPoints;
    int currentHitPointsDelta;
    float multiplier;
    int overflow;
}