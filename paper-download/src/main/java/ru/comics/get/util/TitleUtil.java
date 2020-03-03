package ru.comics.get.util;

import java.util.Arrays;
import java.util.List;

public class TitleUtil {
    
    public static String normalizeForSearch(String s) {
        return s.replaceAll("[-:–]", "")
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
    
    public static String normalizeForSave(String s) {
        return s.replaceAll("[<>:\"/|?*]", "");
    }

    public static List<String> split(String s, String delimiter) {
        return Arrays.asList(s.split(delimiter));
    }
}
