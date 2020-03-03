package ru.comics.get;

import com.google.gson.Gson;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import ru.comics.get.transfer.IntegrationIssueDto;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.comics.get.system.Constants.DateTime.COMICS_DATE_FORMATTER;

public class ComicsMetaUtil {
    private static final String WEEKLY_COMICS_URL_TEMPLATE = "https://leagueofcomicgeeks.com/comic/get_comics?addons=1&list=releases&date_type=week&date={0}&user_id=0&title&view=list&format%5B%5D=1&format%5B%5D=6&order=pulls";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/d/yyyy");
    private static final String SEPARATOR = ";";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    public static List<IntegrationIssueDto> getWeeklyComicsList(LocalDate date) {
        var formattedDate = date.format(DATE_FORMATTER);
        var weeklyComicsUrl = MessageFormat.format(WEEKLY_COMICS_URL_TEMPLATE, URLEncoder.encode(formattedDate, StandardCharsets.UTF_8));
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(weeklyComicsUrl))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return Collections.emptyList();
        }

        var comicsList = (new Gson()).fromJson(response.body(), ComicsList.class);
        var summaryElements = Jsoup.parseBodyFragment(comicsList.list).select(".comic-summary");
        return summaryElements.stream()
                .map(el -> el.select(".comic-title a, .comic-release"))
                .map(ComicsMetaUtil::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static IntegrationIssueDto of(Elements els) {
        var titleAndNumber = els.get(0).text().split("#");
        
        if(titleAndNumber.length < 2) {
            return null;
        }
        
        var spaceIndex = titleAndNumber[1].indexOf(" ");
        var number = titleAndNumber[1];
        if (spaceIndex != -1) {
            number = titleAndNumber[1].substring(0, spaceIndex);
        }

        var meta = els.get(1).text()
                .replaceAll("[^A-Za-z0-9 $.,!&'\\-]", SEPARATOR)
                .split(SEPARATOR);

        return IntegrationIssueDto.builder()
                .title(titleAndNumber[0].trim())
                .number(Double.valueOf(number))
                .publisher(meta[0].trim())
                .publicationDate(LocalDate.parse(meta[1].trim(), COMICS_DATE_FORMATTER))
                .build();
    }

    @Data
    private static class ComicsList {
        private String sidebar_potw;
        private String statbar;
        private Integer count;
        private String toolbar_top;
        private String list;
        private String filters_publishers;
        private Integer addons;
    }
}
