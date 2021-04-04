package com.rayllanderson.springboot2.controller;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.services.AnimeService;
import com.rayllanderson.springboot2.utils.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks //usado para testar a classe em si
    private AnimeController animeController;

    @Mock //TODO: estudar mais a diferença dos 2
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeServiceMock.findAll(ArgumentMatchers.any())).thenReturn(animePage);
    }

    @Test
    void findALL_ReturnsListOfAnimesInsidePage_WhenSuccessful(){
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        Page<Anime> animePage = animeController.findAll(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

}