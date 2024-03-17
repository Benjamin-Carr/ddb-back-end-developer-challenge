package com.example.resources;

import com.example.api.ApiResponse;
import com.example.api.HitPointChange;
import com.example.api.HitPointsResponse;
import com.example.core.Character;
import com.example.services.CharacterService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/characters")
@Produces(MediaType.APPLICATION_JSON)
public class CharacterResource {

    private final CharacterService characterService = new CharacterService();

    @GET
    public ApiResponse<List<Character>> getAllCharacters() {
        return ApiResponse.<List<Character>>builder()
                .message("Characters retrieved.")
                .data(characterService.getAllCharacters())
                .build();
    }

    @GET
    @Path("/{characterId}")
    public ApiResponse<Character> getCharacter(@PathParam("characterId") int characterId) {
        return ApiResponse.<Character>builder()
                .message("Character retrieved.")
                .data(characterService.getCharacter(characterId))
                .build();
    }

    @POST
    @Path("/{characterId}/damage")
    public ApiResponse<HitPointsResponse> damage(@PathParam("characterId") int characterId, @Valid HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.updateHitPoints(characterId, hitPointChange))
                .build();
    }

    @POST
    @Path("/{characterId}/heal")
    public ApiResponse<HitPointsResponse> heal(@PathParam("characterId") int characterId, @Valid HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.updateHitPoints(characterId, hitPointChange))
                .build();
    }

    @POST
    @Path("/{characterId}/temp-hp")
    public ApiResponse<HitPointsResponse> addTempHp(@PathParam("characterId") int characterId, @Valid HitPointChange hitPointChange) {
        return ApiResponse.<HitPointsResponse>builder()
                .message("Hit points updated.")
                .data(characterService.addTempHp(characterId, hitPointChange.getAmount()))
                .build();
    }
}