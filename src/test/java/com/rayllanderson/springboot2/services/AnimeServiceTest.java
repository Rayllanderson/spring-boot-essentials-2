package com.rayllanderson.springboot2.services;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.exceptions.NotFoundException;
import com.rayllanderson.springboot2.repositories.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;


    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeRepositoryMock.findByNameIgnoreCaseContaining(ArgumentMatchers.anyString())).thenReturn(List.of(AnimeCreator.createAnimeWithId()));
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class))).thenReturn(AnimeCreator.createAnimeWithId());
        BDDMockito.doNothing().when(animeRepositoryMock).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    void findALL_ReturnsListOfAnimesInsidePage_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        Page<Anime> animePage = animeService.findAll(PageRequest.of(1, 2));

        Assertions.assertThat(animePage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findALL_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        List<Anime> animes = animeService.findAllNonPageable();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createAnimeWithId().getId();
        Anime anime = animeService.findById(1L);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void findById_ThrowNotFoundException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> animeService.findById(1L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        List<Anime> animes = animeService.findByName("any existing name");

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        //sobrescrevendo o mockito aqui
        BDDMockito.when(animeRepositoryMock.findByNameIgnoreCaseContaining(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("any name that does not exist");

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    void save_ReturnsAnime_WhenSuccessful() {
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createAnimeWithId());
    }

    @Test
    void update_UpdatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.update(AnimePutRequestBodyCreator.createAnimePutRequestBody(), 1L)).doesNotThrowAnyException();
    }

    @Test
    void deleteById_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.deleteById(1L)).doesNotThrowAnyException();
    }
}