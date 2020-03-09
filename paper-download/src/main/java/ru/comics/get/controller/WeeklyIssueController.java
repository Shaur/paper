package ru.comics.get.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.comics.get.security.exception.AuthenticationException;
import ru.comics.get.security.repository.UserRepository;
import ru.comics.get.repository.IssueRepository;
import ru.comics.get.dto.comics.IssueDto;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/issue")
public class WeeklyIssueController {

    private static final Sort WEEKLY_COMICS_SORT = Sort.by(
            Sort.Order.asc("series.name"),
            Sort.Order.asc("number"));

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public WeeklyIssueController(
            IssueRepository issueRepository,
            UserRepository userRepository
    ) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Collection<IssueDto> getAll(@RequestParam(required = false) String date, Principal principal) {
        var userName = principal.getName();
        var user = userRepository.findFirstByUserName(userName);
        if (user.isEmpty())
            throw new AuthenticationException("User not found");

        LocalDate localDate = (date == null) ? LocalDate.now() : LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME);

        var today = localDate.getDayOfWeek().getValue();
        var monday = localDate.minusDays(today + 1);
        var sunday = localDate.plusDays(7 - today);

        var userSeries = user.get().getSeries();

        return issueRepository.findAllByPublicationDateIsBetween(monday, sunday, WEEKLY_COMICS_SORT).stream()
                .map(issue -> IssueDto.builder()
                        .id(issue.getId())
                        .seriesId(issue.getSeries().getId())
                        .name(issue.getSeries().getName())
                        .number(issue.getNumber())
                        .subscribe(userSeries.contains(issue.getSeries()))
                        .build()
                )
                .sorted(Comparator.comparing(IssueDto::getSubscribe).reversed())
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @ExceptionHandler(AuthenticationException.class)
    public void subscribe(@PathVariable Long id, @RequestParam Boolean subscribe, Principal principal) {
        var userName = principal.getName();
        var user = userRepository.findFirstByUserName(userName);

        issueRepository.findById(id).ifPresent(i -> {
            var series = i.getSeries();
            user.ifPresent(u -> {
                if (subscribe)
                    u.getSeries().add(series);
                else
                    u.getSeries().remove(series);

                userRepository.save(u);
            });
        });
    }

}
