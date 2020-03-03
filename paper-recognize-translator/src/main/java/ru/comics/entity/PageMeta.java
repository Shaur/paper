package ru.comics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "page_meta")
@Data
@NoArgsConstructor
public class PageMeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<EnRuWordPair> translations;
}
