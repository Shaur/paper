package ru.comics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.comics.translater.entity.EnRuWordPair;
import ru.comics.translater.transfer.Translate;
import ru.comics.translater.repository.EnRuWordRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private final EnRuWordRepository enRuWordRepository;

    @Autowired
    public TranslationService(EnRuWordRepository enRuWordRepository) {
        this.enRuWordRepository = enRuWordRepository;
    }

    public Collection<Translate> findAllWordsTranslations(Collection<String> words) {
        var collect = enRuWordRepository.findAllByWordIn(words).stream()
                .collect(
                        Collectors.groupingBy(
                                EnRuWordPair::getWord,
                                Collectors.mapping(EnRuWordPair::getTranslate, Collectors.toList())
                        )
                );


        return collect.entrySet().stream()
                .map(t -> new Translate(t.getKey(), t.getValue()))
                .collect(Collectors.toList());
    }
}
