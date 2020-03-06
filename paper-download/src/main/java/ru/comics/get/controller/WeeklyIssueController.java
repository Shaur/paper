package ru.comics.get.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.comics.get.auth.exception.AuthenticationException;
import ru.comics.get.repository.IssueRepository;
import ru.comics.get.repository.SeriesRepository;
import ru.comics.get.transfer.IssueDto;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/issue")
public class WeeklyIssueController {

    private static final Sort WEEKLY_COMICS_SORT = Sort.by(
            Sort.Order.desc("series.subscribe"),
            Sort.Order.asc("number"),
            Sort.Order.asc("series.name"));

    private final IssueRepository issueRepository;
    private final SeriesRepository seriesRepository;

    public WeeklyIssueController(
            IssueRepository issueRepository,
            SeriesRepository seriesRepository
    ) {
        this.issueRepository = issueRepository;
        this.seriesRepository = seriesRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(AuthenticationException.class)
    @Transactional
    public Collection<IssueDto> getAll(@RequestParam(required = false) String date) {
        LocalDate localDate = (date == null) ? LocalDate.now() : LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME);
        
        var today = localDate.getDayOfWeek().getValue();
        var monday = localDate.minusDays(today + 1);
        var sunday = localDate.plusDays(7 - today);

        return issueRepository.findAllByPublicationDateIsBetween(monday, sunday, WEEKLY_COMICS_SORT).stream()
                .map(issue -> IssueDto.builder()
                        .id(issue.getId())
                        .seriesId(issue.getSeries().getId())
                        .name(issue.getSeries().getName())
                        .number(issue.getNumber())
                        .subscribe(issue.getSeries().isSubscribe())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @ExceptionHandler(AuthenticationException.class)
    public void subscribe(@PathVariable Long id, @RequestParam Boolean subscribe) {
        issueRepository.findById(id).ifPresent(i -> {
            var series = i.getSeries();
            series.setSubscribe(subscribe);
            seriesRepository.save(series);
        });
    }

}
