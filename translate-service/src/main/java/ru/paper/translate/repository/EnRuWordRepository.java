package ru.paper.translate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paper.translate.entity.EnRuWordPair;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EnRuWordRepository extends JpaRepository<EnRuWordPair, Long> {

    @Transactional
    List<EnRuWordPair> findByWord(String word);

    @Transactional
    List<EnRuWordPair> findAllByWordIn(List<String> words);
}
