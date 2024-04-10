package dev.boot.controller;


import dev.boot.dto.ArticleDTO;
import dev.boot.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService service;


    @GetMapping("/articles")
    public ResponseEntity<Set<ArticleDTO>> getAllArticles() {
        return ResponseEntity.ok(service.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable String id) {
        return ResponseEntity.ok(service.getArticleById(id));
    }
    @GetMapping("/byAuthor/{author}")
    public ResponseEntity<Set<ArticleDTO>>  getArticleByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(service.getAllArticlesByAuthor(author));
    }
    @GetMapping("/byName/{person_name}")
    public ResponseEntity<Set<ArticleDTO>>  getArticleByPerson(@PathVariable String person_name) {
        return ResponseEntity.ok(service.getAllArticlesByNames(person_name));
    }

    @GetMapping("/byYear/{year}")
    public ResponseEntity<Set<ArticleDTO>>  getArticleByPerson(@PathVariable long year) {
        return ResponseEntity.ok(service.getAllArticlesByYear(year));
    }
    @GetMapping("findByWords/{word}")
    public Set<ArticleDTO> getArticleByword(@PathVariable String word) {
        return service.search(word);
    }

    @PostMapping("/add")
    public ResponseEntity<ArticleDTO> save(@Valid @RequestBody ArticleDTO entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updatePerson( @PathVariable String id, @Valid @RequestBody ArticleDTO articleDTO) {
        return ResponseEntity.ok(service.updateArticleById(id, articleDTO));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<ArticleDTO> deleteById(@PathVariable String id){
        service.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
