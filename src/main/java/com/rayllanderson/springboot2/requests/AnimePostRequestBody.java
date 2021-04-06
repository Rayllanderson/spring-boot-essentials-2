package com.rayllanderson.springboot2.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {

    @NotEmpty(message = "The Anime Name cannot be empty or null")
    @Schema(description = "This is the Anime's name", example = "One Piece")
    private String name;
}
