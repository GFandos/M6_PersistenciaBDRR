/**
 * Created by Gerard on 11/12/2016.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseManager {

    Connection c = null;
    Statement stmt = null;

    public DatabaseManager() {
        try {

            String url = "jdbc:postgresql://172.31.73.191/movieDB";
            Properties props = new Properties();
            props.setProperty("user","admin");
            props.setProperty("password","admin");
            //props.setProperty("ssl","true");
            c = DriverManager.getConnection(url, props);
            Class.forName("org.postgresql.Driver");

            stmt = c.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Movie (id INT PRIMARY KEY NOT NULL, title TEXT UNIQUE, release_date DATE NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Actor (id INT PRIMARY KEY NOT NULL, name TEXT UNIQUE)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Casting (id INT PRIMARY KEY NOT NULL, character TEXT UNIQUE, actor_id INT NOT NULL, movie_id INT NOT NULL, FOREIGN KEY(actor_id) REFERENCES Actor(id), FOREIGN KEY(movie_id) REFERENCES Movie(id))");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql, String table, int id) {
        try {

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("SQL statement " + sql + " executed");

        } catch (SQLException e) {

            System.out.println("SQL execution not done.");
            e.printStackTrace();
        }
    }

    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movies = new ArrayList<>();

        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Movie;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  title = rs.getString("title");
                String  release_date = rs.getString("release_date");

                Movie m = new Movie(title, release_date, id);
                movies.add(m);
                //System.out.println(result);
            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return movies;
    }

    public ArrayList<Movie> getAllMovies(int actor_id) {

        ArrayList<Movie> movies = new ArrayList<>();

        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Movie m, Casting c WHERE (c.actor_id = " + actor_id + " ) AND (m.id = c.movie_id);" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  title = rs.getString("title");
                String  release_date = rs.getString("release_date");

                Movie m = new Movie(title, release_date, id);
                movies.add(m);
            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return movies;
    }

    public ArrayList<Actor> getAllActors(int movieId) {

        ArrayList<Actor> actors = new ArrayList<>();

        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Actor a, Casting c WHERE (c.movie_id = " + movieId + ") AND (a.id = c.actor_id);" );

            while ( rs.next() ) {

                String  name = rs.getString("name");
                String character = rs.getString("character");
                int id = rs.getInt("actor_id");

                Actor a = new Actor(name, id);
                a.setCharacter(character);
                actors.add(a);

            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return actors;
    }

    public ArrayList<Actor> getAllActors() {

        ArrayList<Actor> actors = new ArrayList<>();

        try {

            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Actor a;" );

            while ( rs.next() ) {

                String  name = rs.getString("name");
                int id = rs.getInt("id");
                Actor a = new Actor(name, id);
                actors.add(a);

            }

            rs.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return actors;
    }

    public void closeDatabase() {
        try {
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
