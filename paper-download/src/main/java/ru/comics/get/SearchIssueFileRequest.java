package ru.comics.get;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import ru.comics.get.util.TitleUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class SearchIssueFileRequest {
    private static final String SEARCH_URL = "https://getcomics.info/?s=";

    private final String title;
    private final Double number;

    public SearchIssueFileRequest(String title, Double number) {
        this.title = title;
        this.number = number;
    }

    @SneakyThrows
    public InputStream execute() {
        String searchTitle = TitleUtil.normalizeForSearch(String.format("%s #%.0f", cleanupTitle(), number));
        
        var getComicsFilter = new GetComicsFilter(searchTitle);
        var downloadElement = Jsoup.parse(new URL(SEARCH_URL + URLEncoder.encode(searchTitle, StandardCharsets.UTF_8)), 30_000)
                .select(".post-title a")
                .stream()
                .filter(getComicsFilter)
                .findFirst();

        if (downloadElement.isEmpty())
            return null;

        var postUrl = downloadElement.get().attr("href");
        var postDoc = Jsoup.parse(new URL(postUrl), 30_000);
        var downloadUrl = postDoc.select(".aio-pulse a").first().attr("href");

        var connection = (HttpURLConnection) (new URL(downloadUrl)).openConnection();
        connection.setInstanceFollowRedirects(false);

        String newUrl = connection.getHeaderField("location");

        try(InputStream is = (new URL(newUrl)).openStream()) {
            return is; 
        } catch (Exception ex) {
            log.error("Can't open stream to download. Reason: ", ex);
        }
        
        return null;
    }

    private String cleanupTitle() {
        if (title.startsWith("The "))
            return title.replace("The ", "");

        return title;
    }

    private static class GetComicsFilter implements Predicate<Element> {

        private final List<String> searchString;

        public GetComicsFilter(String searchString) {
            var s = searchString
                    .replaceAll("[-:–]", "")
                    .replaceAll("\\s+", " ");

            this.searchString = TitleUtil.split(s, " ");
        }

        @Override
        public boolean test(Element element) {
            var normalize = TitleUtil.normalizeForSearch(element.text());
            return TitleUtil.split(normalize, " ").containsAll(searchString);
        }
    }
}