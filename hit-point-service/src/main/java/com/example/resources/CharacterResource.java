package com.example.resources;

import com.example.api.ApiResponse;
import com.example.api.Damage;
import com.example.api.HitPointsResponse;
import com.example.services.CharacterService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/character/{characterId}")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final CharacterService characterService = new CharacterService();
    @POST
    @Path("/damage")
    public ApiResponse<HitPointsResponse> damage(Damage damage) {
        return ApiResponse.<HitPointsResponse>builder()
                .id(12345)
                .success(true)
                .message("Hit points updated. Dealt " + damage.getAmount() + " points of " + damage.getDamageType() + " damage.")
                .data(characterService.stub())
                .build();
    }
}