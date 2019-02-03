package fr.florianlallier.psgticketplacemonitor.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {

    private String id;
    private String opponent;
    private String date;
    private int price;

    private Match(String id, String opponent, String date, int price) {
        this.id = id;
        this.opponent = opponent;
        this.date = date;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getOpponent() {
        return opponent;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public static ArrayList<Match> getMatches() {
        ArrayList<Match> result = new ArrayList<>();

        try {
            Document document = Jsoup.connect("https://ticketplace.psg.fr").get();

            Elements matches = document.select("div[class=\"wrapper-block-info-match\"] div[class=\"is-desktop\"]");
            Elements ids = matches.select("div[class=\"btn btn-red acheter\"] a");
            Elements opponents = matches.select("div[class=\"equipe adverse\"] span");
            Elements dates = matches.select("div[class=\"date-match\"]");
            Elements prices = matches.select("div[id=\"group-btn\"] span");

            for (int i = 0; i < matches.size(); i++) {
                String id = ids.get(i).attr("href").replace("/fr/recherche-place/", "");
                String opponent = opponents.get(i).text();
                String date = dates.get(i).text();
                Integer price = Integer.parseInt(prices.get(i).text().replace(" €", ""));

                result.add(new Match(id, opponent, date, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
