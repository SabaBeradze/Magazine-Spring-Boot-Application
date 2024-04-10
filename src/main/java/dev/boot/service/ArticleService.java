package dev.boot.service;

import dev.boot.dto.ArticleDTO;
import dev.boot.domain.Article;
import dev.boot.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository repository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ArticleDTO save(ArticleDTO personDTO) {
        Article person = repository.save(modelMapper.map(personDTO, Article.class));
        return modelMapper.map(person, ArticleDTO.class);
    }

    public ArticleDTO getArticleById(String id) {
        Article person = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "could not find article with id " + id));
        return modelMapper.map(person, ArticleDTO.class);
    }

    public void deleteArticle(String id) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not find Article with id " + id);
        repository.deleteById(id);
    }

    public Set<ArticleDTO> getAllArticles() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(person -> modelMapper.map(person, ArticleDTO.class)).collect(Collectors.toSet());
    }

    public ArticleDTO updateArticleById(String id, ArticleDTO articleDTO) {
        if (!repository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not find article with id " + id);
        articleDTO.setId(id);
        Article article = repository.save(modelMapper.map(articleDTO, Article.class));
        return modelMapper.map(article, ArticleDTO.class);
    }

    public Set<ArticleDTO> getAllArticlesByAuthor(String author) {
        return repository.findArticleByAuthor(author).stream()
                .map(person -> modelMapper.map(person, ArticleDTO.class)).collect(Collectors.toSet());
    }

    public Set<ArticleDTO> getAllArticlesByNames(String names) {
        return repository.findByNamesContaining(names).stream()
                .map(person -> modelMapper.map(person, ArticleDTO.class)).collect(Collectors.toSet());
    }

    public Set<ArticleDTO> getAllArticlesByYear(long year) {
        return repository.findByReleaseYearContainingYear(year).stream()
                .map(person -> modelMapper.map(person, ArticleDTO.class)).collect(Collectors.toSet());
    }

    public Set<ArticleDTO> search(String query) {
        var buildMultiMatchQuery = QueryBuilders.multiMatchQuery(query, "title", "names", "content")
                .fuzziness(Fuzziness.AUTO);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(buildMultiMatchQuery)
                .build();

        SearchHits<Article> searchHits = elasticsearchRestTemplate.search(searchQuery, Article.class);
        return searchHits.getSearchHits().stream()
                .map(searchHit -> modelMapper.map(searchHit.getContent(), ArticleDTO.class))
                .collect(Collectors.toSet());
    }

}
