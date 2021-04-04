package com.rayllanderson.springboot2.controller;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.exceptions.NotFoundException;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.requests.AnimePutRequestBody;
import com.rayllanderson.springboot2.services.AnimeService;
import com.rayllanderson.springboot2.utils.AnimeCreator;
import com.rayllanderson.springboot2.utils.AnimePostRequestBodyCreator;
import com.rayllanderson.springboot2.utils.AnimePutRequestBodyCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks //usado para testar a classe em si
    private AnimeController animeController;

    @Mock //TODO: estudar mais a diferença dos 2
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeServiceMock.findAll(ArgumentMatchers.any())).thenReturn(animePage);
        BDDMockito.when(animeServiceMock.findAllNonPageable()).thenReturn(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(AnimeCreator.createAnimeWithId());
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class))).thenReturn(AnimeCreator.createAnimeWithId());
        BDDMockito.doNothing().when(animeServiceMock).update(ArgumentMatchers.any(AnimePutRequestBody.class),ArgumentMatchers.anyLong());
        BDDMockito.doNothing().when(animeServiceMock).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    void findALL_ReturnsListOfAnimesInsidePage_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        Page<Anime> animePage = animeController.findAll(null).getBody();

        Assertions.assertThat(animePage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findALL_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        List<Anime> animes = animeController.findAllNonPageable().getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createAnimeWithId().getId();
        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void findById_ThrowNotFoundException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong())).thenThrow(new NotFoundException(""));
        Assertions.assertThatThrownBy(() -> animeController.findById(1L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        List<Anime> animes = animeController.findByName("any existing name").getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        //sobrescrevendo o mockito aqui
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("any name that does not exist").getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    void save_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createAnimeWithId());
    }

    @Test
    void update_UpdatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody(), 1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.update(AnimePutRequestBodyCreator.createAnimePutRequestBody(), 1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteById_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1L);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}