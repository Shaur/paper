package ru.comics.utils.xdxf

import org.jsoup.Jsoup
import java.io.File

object XdxfParser {
    private const val charset = "utf-8"

    fun parse(path: String): List<Xdxf> {
        return Jsoup.parse(File(path), charset)
                .select("ar")
                .map { Xdxf(it.select("k").text(), it.text()) }
                .map { clearXdxf(it) }
                .toList()
    }

    private fun clearXdxf(xdfx: Xdxf): Xdxf {
        val translate = xdfx.translate
                .replace(xdfx.word, "")
                .trim()
                .toLowerCase()
        
        val firstQuote = translate.indexOf("\"")
        val lastQuote = translate.lastIndexOf("\"")

        var substring = translate
        if (firstQuote != -1 && lastQuote != -1)
            substring = translate.substring(firstQuote + 1, lastQuote)

        return Xdxf(xdfx.word, substring)
    }
}