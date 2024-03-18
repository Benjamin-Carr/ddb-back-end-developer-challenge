package com.example.core;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    private String name;
    private int level;
    @JsonAlias({"hitPoints"})
    private int maxHitPoints;
    private int currentHitPoints;
    private int tempHitPoints;
    private List<CharacterClass> classes;
    private Stats stats;
    private List<Item> items;
    private List<Defense> defenses;

    // TODO allow deserialization without altering briv.json to include maxHitPoints
}

