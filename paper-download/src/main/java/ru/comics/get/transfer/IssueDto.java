package ru.comics.get.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
