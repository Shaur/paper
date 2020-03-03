package ru.comics.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class TranslateDto implements Serializable {
    
    private final String word;
    private final List<String> translate;
    
}