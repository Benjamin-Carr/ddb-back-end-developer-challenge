package com.example.core;

import com.example.core.enums.DamageMultiplier;
import com.example.core.enums.DamageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Defense {
    @JsonProperty("defense")
    private DamageMultiplier damageMultiplier;
    @JsonProperty("type")
    private DamageType damageType;
}