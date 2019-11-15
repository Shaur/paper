package ru.comics.mq.transfer;

import java.io.Serializable;
import java.util.List;

public class PageRecognitionInfo implements Serializable {
    private List<String> words;
    private String hash;

    public PageRecognitionInfo(List<String> words, String hash) {
        this.words = words;
        this.hash = hash;
    }

    public PageRecognitionInfo() {
    }

    public List<String> getWords() {
        return words;
    }

    public String getHash() {
        return hash;
    }
}
