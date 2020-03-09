package ru.comics.get.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double number;
    
    private LocalDate publicationDate;
    
    @Builder.Default
    private boolean downloaded = false;
    
    @ManyToOne
    @JoinColumn(name = "SERIES_ID")
    private Series series;
}
