package com.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Character {
    private String name;
    private int level;
    private int hitPoints;
    private List<CharacterClass> classes;
    private Stats stats;
    private List<Item> items;
    private List<Defense> defenses;
}
