package ru.comics.get.system;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    
    public static class DateTime {
        
        public static final DateTimeFormatter COMICS_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM d['st']['nd']['rd']['th'], yyyy", Locale.ENGLISH);
        
    }
}
