package com.rayllanderson.springboot2.repositories;

import com.rayllanderson.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Tests for AnimeRepository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    void save_UpdatesAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        animeSaved.setName("Overlord");

        Anime animeUpdated = animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

    }

    @Test
    void delete_RemovesAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
        Assertions.assertThat(animeOptional).isNotPresent();

    }

    @Test
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByNameIgnoreCaseContaining(name);

        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeSaved);

    }

    @Test
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeNotFound(){
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = animeRepository.save(animeToBeSaved);

        List<Anime> animes = this.animeRepository.findByNameIgnoreCaseContaining("this name not exists on database xalalalala");

        Assertions.assertThat(animes).isEmpty();
        Assertions.assertThat(animes).doesNotContain(animeSaved);

    }

    private Anime createAnime(){
        return Anime.builder().name("One piece").build();
    }
}