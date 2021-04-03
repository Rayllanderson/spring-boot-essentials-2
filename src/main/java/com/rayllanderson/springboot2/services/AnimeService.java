package com.rayllanderson.springboot2.services;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.mapper.AnimeMapper;
import com.rayllanderson.springboot2.repositories.AnimeRepository;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Anime findById(long id) throws ResponseStatusException{
        return animeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not Found"));
    }

    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.toAnime(animePostRequestBody));
    }

    public void deleteById(long id) {
        animeRepository.delete(findById(id));
    }

    public void update(AnimePutRequestBody animePutRequestBody, long id) {
        findById(id);
        Anime anime = AnimeMapper.toAnime(animePutRequestBody);
        anime.setId(id);
        animeRepository.save(anime);
    }
}
