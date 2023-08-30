package ds.project1task2;

/*
 * @author Kathy Chiang
 *
 * This class is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to several websites and then screen scraping the HTML or that is
 * returned in order to fabricate an image URL.
 */

import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WorldCupModel {

    Map<String, String> map;
    public WorldCupModel(){
        map = new HashMap<>();
        loadEmoji();
    }

    /**
     * @param country The string "country" indicating the country that the use chose
     * @return the nickname of the chosen country
     */
    public String searchNickName(String country) {
        String URL = "https://www.topendsports.com/sport/soccer/team-nicknames-women.htm";
        String nickname;

        Document doc = null;

        // Use Jsoup to parse the html
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // This code uses Jsoup library to extract information from an HTML document.
        // Get all elements with the tag "table" in the document
        Elements table = doc.getElementsByTag("table");

        // Select the elements with the tag "tbody" from the table elements
        Elements tbody = table.select("tbody");

        // Select the elements with the tag "tr" from the tbody elements
        Elements tr = tbody.select("tr");

        for (Element e : tr.next()) {
            // If the first td element's text in the current row is equal to the country
            if (e.select("td").get(0).text().equals(country)) {
                // Set the nickname variable to the text of the second td element in the current row
                nickname = e.select("td").get(1).text();

                return nickname;
            }
        }

        return "Not Found";
    }

    /**
     * @param country The string "country" indicating the country that the use chose
     * @return the capital city of the chosen country
     */
    public String searchCapital(String country) throws IOException {
        // edge cases
        if (country.equals("South Korea"))
            country = "korea";

        if (country.equals("England"))
            country = "Great%20Britain";

        if (country.equals("United States"))
            country = "USA";

        // check the format of country name
        if (country.contains(" "))
            country = country.replace(" ", "%20");

        // parsing json object/response using fetch()
        String response = fetch("https://restcountries.com/v2/name/" + country);

        // parse string using gson
        if (response != null) {
            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
            JsonObject jsonObject = (JsonObject) jsonArray.get(0);

            return jsonObject.get("capital").getAsString();
        }

        return null;
    }


    /**
     * @param country The string "country" indicating the country that the use chose
     * @return an array string that contains the scorer name and the number of goal
     */
    public String[] searchScorer(String country) {
        String URL = "https://www.espn.com/soccer/stats/_/league/FIFA.WWC/season/2019/view/scoring";
        String scorer;
        String goal;

        Document doc = null;

        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // return the tables in the web page
        Elements tables = doc.getElementsByTag("table");

        // get the first table(top score)
        Elements tbody = tables.get(0).select("tbody");
        Elements tr = tbody.select("tr");

        for (Element e : tr.next()) {
            if (e.select("td").get(2).text().equals(country)) {
                scorer = e.select("td").get(1).text();
                goal = e.select("td").get(4).text();

                return new String[]{scorer + ", ", goal + " goal(s)"};
            }
        }

        return new String[]{"N/A", " "};
    }

    /**
     * @param country The string "country" indicating the country that the use chose
     * @return the URL of the chosen country's flag image
     */
    public String searchFlag(String country) throws UnsupportedEncodingException {
        if (country.equals("England"))
            country = "united-kingdom";
        else if (country.equals("United States"))
            country = "united-states";
        else if (country.equals("Costa Rica"))
            country = "costa-rica";
        else if (country.equals("South Africa"))
            country = "south-africa";
        else if (country.equals("New Zealand"))
            country = "new-zealand";

        country = country.toLowerCase();

        country = URLEncoder.encode(country, "UTF-8");

        String response = "";

        // Create a URL for the page to be screen scraped
        String flagURL = "https://www.cia.gov/the-world-factbook/countries/" + country + "/flag";

        // parse the html web page
        Document doc = null;
        try {
            doc = Jsoup.connect(flagURL).get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // return image in the document object
        // Find the path of image using its attribute "src"
        Elements img = doc.select("img[src$=.jpg]");
        String imgPath = img.attr("src");

        // Now snip out the part from positions cutLeft to cutRight
        // and prepend the protocol (i.e. https).
        String pictureURL = "https://www.cia.gov/" + imgPath;

        return pictureURL;
    }

    /**
     * Put only the needed countries into the map so that it can search for the country using map.get()
     */
    public void loadEmoji() {
        String[] countries =
                {"Argentina",
                        "Australia",
                        "Brazil",
                        "Canada",
                        "China",
                        "Colombia",
                        "Costa Rica",
                        "Denmark",
                        "United Kingdom",
                        "France",
                        "Germany",
                        "Ireland",
                        "Italy",
                        "Jamaica",
                        "Japan",
                        "Morocco",
                        "Netherlands",
                        "New Zealand",
                        "Nigeria",
                        "Norway",
                        "Philippines",
                        "South Africa",
                        "South Korea",
                        "Spain",
                        "Sweden",
                        "Switzerland",
                        "United States",
                        "Vietnam",
                        "Zambia"};

        for (String c : countries) {
            map.put(c, null);
        }

        String URL = "https://cdn.jsdelivr.net/npm/country-flag-emoji-json@2.0.0";
        String jsonResponse = fetch(URL);

        if (jsonResponse != null) {
            // parse json object to a jsonArray
            JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();

            Gson g = new Gson();

            // parse jsonArray using gson to get names of countries from String "URL"
            for (JsonElement element : jsonArray) {
                countryInfo countryInfo = g.fromJson(element, countryInfo.class);
                String name = countryInfo.getName();

                // if the country name exits in the list of needed conutries, put the image link into the map
                if(map.containsKey(name))
                    map.put(name, countryInfo.getImage());
            }
        }
    }

    /**
     * @param country The string "country" indicating the country that the use chose
     * @return the URL of the chosen country's flag emoji
     */
    public String searchEmoji (String country){
        if(country.equals("England"))
            country = "United Kingdom";

        return map.get(country);
    }

    /**
     * Make an HTTP request to a given URL
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.
     * This is identical to what would be returned from using curl on the command line.
     */
    private String fetch (String urlString){
        String response = "";
        try {
            URL url = new URL(urlString);
            // making request to Http and build connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Read all the text returned by the server // reading response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;

            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        return response;
    }
}
