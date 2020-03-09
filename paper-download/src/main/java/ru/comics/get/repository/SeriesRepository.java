package ru.comics.get.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.comics.get.entity.Series;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    @Query("select s from Series s where s.name = :name and s.publisher = :publisher and (s.startYear >= :year and :year <= s.endYear)")
    List<Series> findAllByNameAndYear(String name, String publisher, Integer year);
}
