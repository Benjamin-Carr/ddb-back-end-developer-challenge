package com.example.api;

import com.example.core.enums.DamageType;
import com.example.services.DamageValidator;
import jakarta.validation.constraints.AssertTrue;
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

    @AssertTrue(message = "damageType must not be null when amount is negative")
    // TODO method should be named isValid but Hibernator prepends the message with the method name after the "is" part,
    //  so the correct name makes the message look bad.
    boolean isValidation() {
        return DamageValidator.isValid(this);
    }
}