package com.rayllanderson.springboot2.utils;

import com.rayllanderson.springboot2.domain.Anime;

public class AnimeCreator {


    /**
     * @return anime sem ID, apenas com nome
     */
    public static Anime createAnimeToBeSaved(){
        return Anime.builder().name("One piece").build();
    }


    /**
     * @return anime com nome e com id = 1L
     */
    public static  Anime createAnimeWithId(){
        return Anime.builder().name("One piece").id(1L).build();
    }

    /**
     * @return anime com nome diferente e com id 1L
     */
    public static  Anime createAnimeToBeUpdated(){
        return Anime.builder().name("One piece 2").id(1L).build();
    }

}
