package com.rayllanderson.springboot2.controller;

import com.rayllanderson.springboot2.domain.Anime;
import com.rayllanderson.springboot2.requests.AnimePostRequestBody;
import com.rayllanderson.springboot2.requests.AnimePutRequestBody;
import com.rayllanderson.springboot2.services.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/animes")
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    @Operation(summary = "List all animes paginated", description = "The default size is 5. Use the parameter size to change the " +
            "default value", tags = {"anime"})
    public ResponseEntity<Page<Anime>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(animeService.findAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Anime>> findAllNonPageable() {
        return ResponseEntity.ok(animeService.findAllNonPageable());
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.save(anime));
    }

    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "When successful operation"),
            @ApiResponse(responseCode = "404", description = "When Anime Does Not exist in the Database")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid AnimePutRequestBody anime, @PathVariable long id) {
        animeService.update(anime, id);
        return ResponseEntity.noContent().build();
    }
}
