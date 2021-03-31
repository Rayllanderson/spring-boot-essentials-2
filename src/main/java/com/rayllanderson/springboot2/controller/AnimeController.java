package com.rayllanderson.springboot2.controller;

import com.rayllanderson.springboot2.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/animes")
public class AnimeController {

    @GetMapping
    public List<Anime> findAll(){
        return List.of(new Anime("Berserk"), new Anime("Horimiya"));
    }
}
