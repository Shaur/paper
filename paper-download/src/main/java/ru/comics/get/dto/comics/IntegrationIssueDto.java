package ru.comics.get.dto.comics;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class IntegrationIssueDto {

    private String title;
    private Double number;
    private String publisher;
    private LocalDate publicationDate;

}
