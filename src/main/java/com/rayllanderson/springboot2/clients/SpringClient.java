package com.rayllanderson.springboot2.clients;

import com.rayllanderson.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        final String API_URL = "http://localhost:8080/animes";

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity(API_URL + "/{id}", Anime.class, 2);
        log.info(entity);
        Anime anime = new RestTemplate().getForObject(API_URL + "/{id}", Anime.class, 2);
        log.info("-------");
        log.info(anime);


        log.info("-------");
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(API_URL + "/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
        });
        log.info(exchange.getBody());


        //POST com postForObject
        //        log.info("-------");
        //        Anime nagatoro = Anime.builder().name("Nagotoro").build();
        //        Anime nagatodoSaved = new RestTemplate().postForObject(API_URL, nagatoro, Anime.class);
        //        log.info(nagatodoSaved);

        //POST com exchange
        Anime onePunchMan = Anime.builder().name("One Punch Man").build();
        ResponseEntity<Anime> savedOnePunchMan = new RestTemplate().exchange(API_URL, HttpMethod.POST, new HttpEntity<>(onePunchMan,
                createJsonHeader()), Anime.class);
        log.info("-------");
        log.info(savedOnePunchMan);


        Anime animeToBeUpdated = savedOnePunchMan.getBody();
        animeToBeUpdated.setName("One Punch Man 2");
        ResponseEntity<Void> onePunchManUpdated = new RestTemplate().exchange(API_URL + "/{id}", HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()), Void.class, animeToBeUpdated.getId());
        log.info("Updated: {}", onePunchManUpdated);


        ResponseEntity<Void> onePunchManDelete = new RestTemplate().exchange(API_URL + "/{id}", HttpMethod.DELETE,
                null, Void.class, animeToBeUpdated.getId());
        log.info("Deleted: {}", onePunchManDelete);

    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
