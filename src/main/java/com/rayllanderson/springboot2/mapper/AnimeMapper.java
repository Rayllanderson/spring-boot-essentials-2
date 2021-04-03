package com.rayllanderson.springboot2.mapper;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.requests.AnimePutRequestBody;
import org.modelmapper.ModelMapper;

public class AnimeMapper {

    public static Anime toAnime(AnimePostRequestBody animePostRequestBody){
        return new ModelMapper().map(animePostRequestBody, Anime.class);
    }

    public static Anime toAnime(AnimePutRequestBody animePutRequestBody){
        return new ModelMapper().map(animePutRequestBody, Anime.class);
    }

    public static AnimePostRequestBody toAnimePostRequestBody(Anime anime){
        return new ModelMapper().map(anime, AnimePostRequestBody.class);
    }

    public static AnimePutRequestBody toAnimePuRequestBody(Anime anime){
        return new ModelMapper().map(anime, AnimePutRequestBody.class);
    }

}
