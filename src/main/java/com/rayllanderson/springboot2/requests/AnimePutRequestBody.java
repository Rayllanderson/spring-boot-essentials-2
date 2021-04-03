package com.rayllanderson.springboot2.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePutRequestBody {

    private Long id;

    @NotEmpty(message = "The Anime Name cannot be empty or null")
    private String name;
}
