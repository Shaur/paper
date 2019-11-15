package ru.paper.translate.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "en_ru_word")
public class EnRuWordPair {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "en_ru_pair_seq")
    @SequenceGenerator(name = "en_ru_pair_seq", sequenceName = "en_ru_pair_seq")
    private Long id;

    @Column(name = "word")
    private String word;

    @Lob
    @Column(name = "translate")
    private String translate;

    public EnRuWordPair() {
    }

    public EnRuWordPair(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnRuWordPair that = (EnRuWordPair) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(word, that.word) &&
                Objects.equals(translate, that.translate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, translate);
    }

    @Override
    public String toString() {
        return "EnRuWordPair{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", translate='" + translate + '\'' +
                '}';
    }
}
