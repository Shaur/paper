package ru.comics.get.dto.comics;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {

    private Long id;
    private Long seriesId;
    private String name;
    private Double number;
    private Boolean subscribe;

}


