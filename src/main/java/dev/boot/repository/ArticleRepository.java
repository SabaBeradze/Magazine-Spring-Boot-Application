package dev.boot.repository;

import dev.boot.domain.Article;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface ArticleRepository extends ElasticsearchRepository<Article,String> {

    Set<Article> findArticleByAuthor(String author);

    Set<Article> findByNamesContaining(String name);

    @Query("{\"range\": {\"@timestamp\": {\"gte\": \"?0-01-01\", \"lt\": \"?0-12-31\"}}}")
    Set<Article> findByReleaseYearContainingYear(long year);



}
