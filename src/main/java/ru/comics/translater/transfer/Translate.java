package ru.comics.translater.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Translate implements Serializable {
    
    private final String word;
    private final String translate;
    
}