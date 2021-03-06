package ru.nkorochkin.Fonbet.Parser.parser;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nkorochkin.Fonbet.Parser.model.Model;
import ru.nkorochkin.Fonbet.Parser.model_parts.event.Event;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class Parser {

    private static final Gson GSON = new Gson();

    @Scheduled(fixedRateString = "${fonbet.parser.frequency.ms}")
    public void getModel() {
        try {
            String liveJson = Jsoup.connect("https://line31.bkfon-resources.com/line/mobile/showEvents?sysId=2&lang=ru&scopeMarket=1600&lineType=live_line&skId=1")
                    .ignoreContentType(true)
                    .execute()
                    .body();
            System.out.println("Connected");
            Model model = GSON.fromJson(liveJson, Model.class);
            Map<Event, List<Event>> finalMap = model.getResult();
            System.out.println(finalMap);
        } catch (IOException e) {
            System.out.println("Connection failed " + e.getMessage());
        }
    }
}



