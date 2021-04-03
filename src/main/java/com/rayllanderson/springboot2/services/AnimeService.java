package com.rayllanderson.springboot2.services;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.exceptions.NotFoundException;
import com.rayllanderson.springboot2.mapper.AnimeMapper;
import com.rayllanderson.springboot2.repositories.AnimeRepository;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Page<Anime> findAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findAllNonPageable() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByNameIgnoreCaseContaining(name);
    }

    public Anime findById(long id) throws ResponseStatusException {
        return animeRepository.findById(id).orElseThrow(() -> new NotFoundException("Anime not Found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.toAnime(animePostRequestBody));
    }

    @Transactional
    public void deleteById(long id) {
        animeRepository.delete(findById(id));
    }

    @Transactional
    public void update(AnimePutRequestBody animePutRequestBody, long id) {
        findById(id);
        Anime anime = AnimeMapper.toAnime(animePutRequestBody);
        anime.setId(id);
        animeRepository.save(anime);
    }
}
