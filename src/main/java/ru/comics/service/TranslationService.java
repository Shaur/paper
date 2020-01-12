package ru.comics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.comics.translater.transfer.Translate;
import ru.comics.translater.entity.EnRuWordPair;
import ru.comics.translater.repository.EnRuWordRepository;
import ru.comics.utils.xdxf.Xdxf;
import ru.comics.utils.xdxf.XdxfParser;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/api/v1")
public class TranslationService {

    private final EnRuWordRepository enRuWordRepository;

    @Autowired
    public TranslationService(EnRuWordRepository enRuWordRepository) {
        this.enRuWordRepository = enRuWordRepository;
    }

    @GetMapping("init")
    public void init(@RequestParam("path") String path) {
        List<Xdxf> xdxfList = XdxfParser.INSTANCE.parse(path);
        List<EnRuWordPair> enRuWordPairs = xdxfList.stream()
                .map(xdxf -> new EnRuWordPair(xdxf.getWord(), xdxf.getTranslate()))
                .collect(Collectors.toList());
        System.out.println(enRuWordPairs.size());
        enRuWordRepository.saveAll(enRuWordPairs);
    }


    @GetMapping(value = "translate/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Translate translateEn(@PathVariable("word") String word) {
        List<EnRuWordPair> translation = enRuWordRepository.findByWord(word);
        return translation.stream()
                .findFirst()
                .map(p -> new Translate(p.getWord(), p.getTranslate()))
                .get();
    }

//    @GetMapping(value = "translateAll/{words}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<Translate> translateEn(@PathVariable("words") List<String> words) {
//        List<EnRuWordPair> allByWords = enRuWordRepository.findAllByWordIn(words);
//        return allByWords.stream()
//                .map(enRuWordPair -> new Translate(enRuWordPair.getWord(), enRuWordPair.getTranslate()))
//                .collect(Collectors.toList());
//    }
}
