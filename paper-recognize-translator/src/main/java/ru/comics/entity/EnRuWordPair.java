package ru.comics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "en_ru_word")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnRuWordPair {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "en_ru_pair_seq")
    @SequenceGenerator(name = "en_ru_pair_seq", sequenceName = "en_ru_pair_seq")
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "translate")
    private String translate;

    public EnRuWordPair(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }
}
