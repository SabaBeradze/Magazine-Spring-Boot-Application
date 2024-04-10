package dev.boot.dto;

import dev.boot.domain.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Validated
public class ArticleDTO {
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String author;
    @Past(message = "Date must be in the past")
    private LocalDate releaseYear;
    @Past(message = "Date must be in the past")
    private LocalDate lastUpdateDate;

    private Area area;

    private Set<@NotBlank String> names;

    @Schema(hidden = true)
    public void setId(String id) {
        this.id = id;
    }
}
