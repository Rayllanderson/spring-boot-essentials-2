package com.rayllanderson.springboot2.integration;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.repositories.AnimeRepository;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.utils.AnimeCreator;
import com.rayllanderson.springboot2.utils.AnimePostRequestBodyCreator;
import com.rayllanderson.springboot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    void findALL_ReturnsListOfAnimesInsidePage_WhenSuccessful() {
        animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = AnimeCreator.createAnimeWithId().getName();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
        }).getBody();

        Assertions.assertThat(animePage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findALL_ReturnsListOfAnimes_WhenSuccessful() {
        animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = AnimeCreator.createAnimeWithId().getName();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findById_ReturnsAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();
        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void findById_ThrowNotFoundException_WhenAnimeIsNotFound() {
        ResponseEntity<Anime> entity = testRestTemplate.getForEntity("/animes/69895", Anime.class);
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = AnimeCreator.createAnimeWithId().getName();
        String url = String.format("/animes/find?name=%s", expectedName);
        List<Anime> animes =testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        List<Anime> animes =testRestTemplate.exchange("/animes/find?name=whatever", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void update_UpdatesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");
        String url = "/animes/" + savedAnime.getId();
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(savedAnime),
                Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void deleteById_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String url = "/animes/" + savedAnime.getId();
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE,
                null,
                Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


}
