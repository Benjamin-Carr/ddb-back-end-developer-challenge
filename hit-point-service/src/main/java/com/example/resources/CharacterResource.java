package com.example.resources;

import com.example.api.ApiResponse;
import com.example.api.Damage;
import com.example.api.HitPointsResponse;
import com.example.services.CharacterService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/character/{characterId}")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final CharacterService characterService = new CharacterService();
    @POST
    @Path("/damage")
    public ApiResponse<HitPointsResponse> damage(@PathParam("characterId") int characterId, Damage damage) {
        HitPointsResponse data = characterService.dealDamage(characterId, damage);
        return ApiResponse.<HitPointsResponse>builder()
                .id(12345)
                .success(true)
                .message("Hit points updated. Dealt " + damage.getAmount() + " points of " + damage.getDamageType() + " damage.")
                .data(data)
                .build();
    }
}