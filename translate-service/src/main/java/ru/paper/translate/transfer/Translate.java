package ru.paper.translate.transfer;

import java.io.Serializable;
import java.util.Objects;

public class Translate implements Serializable {
    private String word;
    private String translate;

    public Translate (String word, String translate) {
        this.word = word;
        this.translate = translate;
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
        Translate translate1 = (Translate) o;
        return Objects.equals(word, translate1.word) &&
                Objects.equals(translate, translate1.translate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, translate);
    }

    @Override
    public String toString() {
        return "Translate{" +
                "word='" + word + '\'' +
                ", translate='" + translate + '\'' +
                '}';
    }
}