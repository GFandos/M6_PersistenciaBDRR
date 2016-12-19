/**
 * Created by 47989768s on 12/12/16.
 */

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private MovieDbApi api;
    private JsonManager jm;
    private DatabaseManager dm;

    Scanner sc;
    ArrayList<Movie> movies;
    ArrayList<Casting> castings;
    ArrayList<Actor> actors;

    private int wantedFilms;
    private int minFilmId;

    public Menu(Scanner _sc) {
        api = new MovieDbApi();
        jm = new JsonManager();
        dm = new DatabaseManager();
        wantedFilms = 10;
        minFilmId = 100;

        sc = _sc;

    }

    public void init() {

        //clearScreen();
        System.out.println("***************************************************************");
        System.out.println("					Welcome to MovieDB!");
        System.out.println("***************************************************************");
        System.out.println("		1. Change wanted films (default 10).");
        System.out.println("		2. Change first film id (default 100).");
        System.out.println("		3. Get movies from api.");
        System.out.println("		4. List stored movies.");
        System.out.println("		5. List stored actors.");
        System.out.println("		6. Exit program.");

        int selectedOption = 0;

        if (sc.hasNextInt()) selectedOption = sc.nextInt();
        else {
            System.out.println("Only numeric characters.");
        }

        switch(selectedOption) {

            case 1:
                changeWantedFilms(sc);
                break;

            case 2:
                changeFirstFilmId(sc);
                break;

            case 3:
                getMoviesFromApi();
                break;

            case 4:
                listAllMovies();
                break;

            case 5:
                listAllActors();
                break;

            case 6:
                exitProgram();
                break;

            default:
                System.out.println("Invalid option.");
        }


    }

    private void listAllActors() {

        ArrayList<Actor> wantedActors = dm.getAllActors();

        System.out.println("------------------ Retrieved Actors ------------------");
        for(int i = 0; i < wantedActors.size(); ++i) {
            System.out.println(wantedActors.get(i).getId() + " - " + wantedActors.get(i).getName());
        }

        System.out.println("To select an actor to retrieve it's information write the actor id. Or write 0 to return to main menu.");
        int selectedOption = 0;
        if (sc.hasNextInt()) selectedOption = sc.nextInt();
        else {
            System.out.println("Only numeric characters.");
            return;
        }

        if (selectedOption == 0) return;

        ArrayList<Movie> wantedMovies = dm.getAllMovies(selectedOption);
        showFilmsInformation(wantedMovies, selectedOption);
        
    }

    private void showFilmsInformation(ArrayList<Movie> wantedMovies, int selectedOption) {

        System.out.println("------------------ Movies where actor " + selectedOption + " worked ------------------");
        for(int i = 0; i < wantedMovies.size(); ++i) {
            System.out.println(wantedMovies.get(i).getId() + " - " + wantedMovies.get(i).getTitle() + " (" + wantedMovies.get(i).getRelease_date() + ")");
        }

    }

    private void listAllMovies() {

        ArrayList<Movie> wantedMovies = dm.getAllMovies();

        System.out.println("------------------ Retrieved movies ------------------");
        for(int i = 0; i < wantedMovies.size(); ++i) {
            System.out.println(wantedMovies.get(i).getId() + " - " + wantedMovies.get(i).getTitle() + " (" + wantedMovies.get(i).getRelease_date() + ")");
        }

        System.out.println("To select a film to retrieve it's information write the film id. Or write 0 to return to main menu.");
        int selectedOption = 0;
        if (sc.hasNextInt()) selectedOption = sc.nextInt();
        else {
            System.out.println("Only numeric characters.");
            return;
        }

        if (selectedOption == 0) return;

        ArrayList<Actor> wamtedActors = dm.getAllActors(selectedOption);
        showActorsInformation(wamtedActors, selectedOption);

    }

    private void showActorsInformation(ArrayList<Actor> wamtedActors, int filmId) {

        System.out.println("------------------ Actors who worked on the film " + filmId + " ------------------");
        for(int i = 0; i < wamtedActors.size(); ++i) {
            System.out.println("       " + wamtedActors.get(i).getId() + "- " + wamtedActors.get(i).getName() + " as " + wamtedActors.get(i).getCharacter() + ".");
        }

    }

    private void getMoviesFromApi() {

        ArrayList<JSONObject> jsonMovies = api.getMovie(minFilmId, wantedFilms);
        ArrayList<JSONObject> jsonMovieCredits = api.getMovieCredits(minFilmId, wantedFilms);
        jm.manageMovies(jsonMovies);
        jm.manageMovieCredits(jsonMovieCredits, minFilmId);

        movies = jm.getMovies();
        castings = jm.getCastings();
        actors = jm.getActors();

        for (int i = 0; i < movies.size(); ++i) {
            movies.get(i).insertToDatabase(dm);
        }
        for (int i = 0; i < castings.size(); ++i) {
            castings.get(i).insertToDatabase(dm);
        }
        for (int i = 0; i < actors.size(); ++i) {
            actors.get(i).insertToDatabase(dm);
        }

        System.out.println("Movies correctly retrieved and stored.\n\n");

    }

    private void changeWantedFilms(Scanner sc) {
        System.out.println("How many films do you want?");

        int selectedOption = 0;
        if (sc.hasNextInt()) selectedOption = sc.nextInt();
        else {
            System.out.println("Only numeric characters.");
            return;
        }

        wantedFilms = selectedOption;
        System.out.println("Action done.");

    }

    private void changeFirstFilmId(Scanner sc) {
        System.out.println("From what film do you want to start retrieving?");

        int selectedOption = 0;
        if (sc.hasNextInt()) selectedOption = sc.nextInt();
        else {
            System.out.println("Only numeric characters.");
            return;
        }

        minFilmId = selectedOption;
        System.out.println("Action done.");

    }

    private void exitProgram() {
        sc.close();
        System.exit(0);
    }

    public void clearScreen() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

}
