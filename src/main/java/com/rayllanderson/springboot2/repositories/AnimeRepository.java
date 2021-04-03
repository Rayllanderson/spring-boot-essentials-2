package com.rayllanderson.springboot2.repositories;

import com.rayllanderson.springboot2.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

}
