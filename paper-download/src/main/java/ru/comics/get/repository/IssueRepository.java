package ru.comics.get.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.comics.get.entity.Issue;
import ru.comics.get.entity.Series;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    List<Issue> findAllByPublicationDateIsBetween(LocalDate startDay, LocalDate endDay, Sort sort);
    
    Optional<Issue> findFirstBySeriesAndAndNumber(Series series, Double number);
    
    @Query("select i from Issue i where i.downloaded = false")
    List<Issue> getNotDownloadedIssues();
}
