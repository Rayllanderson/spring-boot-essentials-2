package com.rayllanderson.springboot2.utils;

import com.rayllanderson.springboot2.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createAnimeToBeUpdated().getId())
                .name(AnimeCreator.createAnimeToBeUpdated().getName())
                .build();
    }
}
