/**
 * Created by Gerard on 11/12/2016.
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class MovieDbApi {

    private static String uri = "https://api.themoviedb.org/3/movie/";
    private static String key = "612e3444bda114d49c3b4d6548823c2e";
    private static String credits = "/credits";

    public ArrayList<JSONObject> getMovie(int minMovieId, int numMovies) {

        ArrayList<JSONObject> movies = new ArrayList<>();

        try {

            for (int i = 0; i < numMovies; ++i) {

                String url = uri + minMovieId + "?api_key=" + key;
                System.out.println(url);

                JSONParser parser = new JSONParser();
                JSONObject jsonO = (JSONObject) parser.parse(HttpUtils.get(url));
                movies.add(jsonO);

                System.out.println(jsonO.toJSONString());

                minMovieId++;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public ArrayList<JSONObject> getMovieCredits(int minMovieId, int numMovies) {

        ArrayList<JSONObject> movieCredits = new ArrayList<>();

        try {

            for (int i = 0; i < numMovies; ++i) {

                String url = uri + minMovieId + credits + "?api_key=" + key;
                System.out.println(url);

                JSONParser parser = new JSONParser();
                JSONObject jsonO = (JSONObject) parser.parse(HttpUtils.get(url));
                movieCredits.add(jsonO);

                System.out.println(jsonO.toJSONString());

                minMovieId++;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieCredits;
    }

}