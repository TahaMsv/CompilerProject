
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SyntaxHighlighter {
    private final Document document;
    private int line = 1;

    public SyntaxHighlighter() {
        String html = "<html><head><title>Highlighter</title></head>"
                + "<body style=\"background-color:#2d3e50;font-family:monospace;\">"
                + "<div id='tokens'> <p>" + "<span style=" + "\"color:lightgrey\">" + line + "</span>" + "&nbsp;" + "</p>"
                + "</body></html>";
        document = Jsoup.parse(html);
    }

    public Document getDocument() {
        return document;
    }

    public void addHtmlText(String tokenValue, String tokenType) {
        Element p = document.getElementsByTag("p").first();
        if (tokenType != Main.LINE_TERMINATOR) {
            Element span = new Element("span");
            span.attr("style", getStyle(tokenType));
            span.append(getRightTextFormat(tokenValue)).append(" ");
            p.append(span.outerHtml());
        } else {
            Element br = new Element("br");
            p.append(br.outerHtml());
        }
    }

    private String newLineWithGreyStyle(int line) {
        return "<br><span style=" + "\"color:lightgrey\">" + line + "</span>";
    }

    private String getRightTextFormat(String text) {
        text = text.replaceAll(" ", "&nbsp;")
                .replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
                .replaceAll("\r", "");
        while (text.contains("\n")) {
            text = text.replaceFirst("\n", newLineWithGreyStyle(++line));
        }
        return text;
    }

    private String getStyle(String tokenType) {
        String font = "font-weight:";
        String color = "color:";
        switch (tokenType) {
            case Main.RESERVED_KEYWORDS:
                color += "#FC618D";
                font += "normal";
                break;
            case Main.IDENTIFIERS:
                color += "#FFFFFF";
                font += "normal";
                break;
            case Main.INTEGER_NUMBERS:
                color += "#F59762";
                font += "normal";
                break;
            case Main.REAL_NUMBERS:
                color += "#F59762";
                font = "font-style:italic";
                break;
            case Main.STRINGS:
                color += "#FCE566";
                font += "normal";
                break;
            case Main.SPECIAL_CHARACTERS:
                color += "#EE82EE";
                font = "font-style:italic";
                break;
            case Main.COMMENTS:
                color += "#69676C";
                font += "normal";
                break;
            case Main.OPERATORS_AND_PUNCTUATIONS:
                color += "#00FFFF";
                font += "normal";
                break;
            case Main.UNDEFINED_TOKEN:
            default:
                color += "#FF0000";
                font += "normal";
                break;
        }
        return color + ";" + font;
    }

    public void writeToHtml(String html, String path) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(html);
        }
    }
}