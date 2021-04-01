package com.rayllanderson.springboot2.services;

import com.rayllanderson.springboot2.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {

    private List<Anime> animes = List.of(new Anime(1L, "Berserk"), new Anime(2L, "Kaguya sama"));
    public List<Anime> findAll(){
        return animes;
    }

    public Anime findById(long id){
        return animes.stream().filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not Found"));
    }

}
