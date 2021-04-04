package com.rayllanderson.springboot2.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AnimePostRequestBody {

    @NotEmpty(message = "The Anime Name cannot be empty or null")
    private String name;
}
