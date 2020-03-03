package ru.comics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.comics.entity.EnRuWordPair;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface EnRuWordRepository extends JpaRepository<EnRuWordPair, Long> {

    @Transactional
    List<EnRuWordPair> findByWord(String word);

    @Transactional
    @Query("select p from EnRuWordPair p where p.word in :words")
    Collection<EnRuWordPair> findAllByWordIn(@Param("words") Collection<String> words);
}
