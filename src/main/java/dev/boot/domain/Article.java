package dev.boot.domain;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Document(indexName = "articles2")
@Validated
@NoArgsConstructor
public class Article {
    @Id
    private  String id;

    @NotBlank
    private  String title;

    @NotBlank
    private  String content;

    @NotBlank
    private  String author;

    @Field(name = "@timestamp", type = FieldType.Date)
    @Past(message = "Date must be in the past")
    private  LocalDate releaseYear;

    @Field(name = "@timestamp1", type = FieldType.Date)
    @Past(message = "Date must be in the past")
    private  LocalDate lastUpdateDate;

    @Field(type = FieldType.Text, fielddata = true)
    private  Area area;

    private Set<@NotBlank String> names;


}
