package com.example.api;

import com.example.core.enums.DamageType;
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
public class HitPointChange {
    private int amount;
    private DamageType damageType;

    public HitPointChange(int amount) {
        this.amount = amount;
    }
}