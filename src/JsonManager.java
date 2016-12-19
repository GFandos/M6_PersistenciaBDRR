import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Gerard on 11/12/2016.
 */
public class JsonManager {

    ArrayList<Movie> movies;
    ArrayList<Casting> castings;
    ArrayList<Actor> actors;

    public JsonManager() {
        movies = new ArrayList<>();
        castings = new ArrayList<>();
        actors = new ArrayList<>();
    }


    public void manageMovies (ArrayList<JSONObject> jsonMovies) {

        for(int i = 0; i < jsonMovies.size(); ++i) {

            int id;
            String title, overview;

            id = Integer.parseInt(jsonMovies.get(i).get("id").toString());
            title = jsonMovies.get(i).getOrDefault("title", "no title found").toString();
            overview = jsonMovies.get(i).getOrDefault("release_date", "no release_date found").toString();

            Movie m = new Movie(title, overview, id);
            movies.add(m);
        }
    }

    public void manageMovieCredits (ArrayList<JSONObject> jsonMovieCredits, int minMovieId) {

        int movie_id = minMovieId;

        for(int j = 0; j < jsonMovieCredits.size(); ++j) {

            JSONArray jArray = (JSONArray) jsonMovieCredits.get(j).get("cast");
            Iterator i = jArray.iterator();
            while(i.hasNext()){

                JSONObject o = (JSONObject) i.next();

                if(o.toString().contains("character")) {

                    String character = o.get("character").toString();
                    int id = Integer.parseInt(o.get("cast_id").toString());
                    int actor_id = Integer.parseInt(o.get("id").toString());

                    Casting c = new Casting(id, character, actor_id, movie_id);

                    String actor_name = o.get("name").toString();

                    Actor a = new Actor(actor_name, actor_id);

                    castings.add(c);
                    actors.add(a);

                }
            }
            movie_id++;

        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Casting> getCastings() {
        return castings;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

}
