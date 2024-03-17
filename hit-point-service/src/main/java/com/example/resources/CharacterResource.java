package com.example.resources;

import com.example.api.ApiResponse;
import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.services.CharacterService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/character/{characterId}")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final CharacterService characterService = new CharacterService();

    @POST
    @Path("/damage")
    public ApiResponse<HitPointsResponse> damage(@PathParam("characterId") int characterId, @Valid HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.updateHitPoints(characterId, hitPointChange))
                .build();
    }

    @POST
    @Path("/heal")
    public ApiResponse<HitPointsResponse> heal(@PathParam("characterId") int characterId, @Valid HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.updateHitPoints(characterId, hitPointChange))
                .build();
    }

    @POST
    @Path("/temp-hp")
    public ApiResponse<HitPointsResponse> addTempHp(@PathParam("characterId") int characterId, HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.addTempHp(characterId, hitPointChange.getAmount()))
                .build();
    }
}