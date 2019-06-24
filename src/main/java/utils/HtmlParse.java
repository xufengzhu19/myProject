package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParse {
    public static String parse(String ohtml){
        String html=ohtml;
        Document doc= Jsoup.parse(html);
        Elements rows = doc.select("table").get(0).select("tr");
        Element row=rows.get(1);
        Element td=row.select("td").get(0);
        Elements ps=td.select("p");
        String txt=ps.get(1).text();
        return txt;
    }
}
