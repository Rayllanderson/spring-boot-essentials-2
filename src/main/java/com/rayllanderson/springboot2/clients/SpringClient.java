package com.rayllanderson.springboot2.clients;

import com.rayllanderson.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);
        Anime anime = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info("-------");
        log.info(anime);

        log.info("-------");
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
        });
        log.info(exchange.getBody());

    }
}
