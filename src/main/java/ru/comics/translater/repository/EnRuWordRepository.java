package ru.comics.translater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.comics.translater.entity.EnRuWordPair;
import ru.comics.translater.transfer.Translate;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface EnRuWordRepository extends JpaRepository<EnRuWordPair, Long> {

    @Transactional
    List<EnRuWordPair> findByWord(String word);

    @Transactional
    @Query("select new ru.comics.translater.transfer.Translate(t.word, t.translate)" +
            "   from EnRuWordPair t " +
            "   where t.word in :words")
    List<Translate> findAllByWordIn(@Param("words") Collection<String> words);
}
