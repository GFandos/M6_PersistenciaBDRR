import java.util.Random;

/**
 * Created by Gerard on 11/12/2016.
 */
public class Casting {

    private int id;
    private String character;
    private int actorId;
    private int movieId;

    public Casting(int _id, String _character, int _actorId, int _movieId) {
        id = _id;
        character = _character;
        actorId = _actorId;
        movieId = _movieId;
    }

    public void insertToDatabase(DatabaseManager dbm) {

        Random r = new Random();

        int newId = id + r.nextInt(1000);

        String rule = "CREATE RULE \"Casting_on_duplicate_ignore\" AS ON INSERT TO \"Casting\" WHERE EXISTS(SELECT 1 FROM Casting WHERE (id = NEW.id)) DO INSTEAD NOTHING;\n";

        String sql = "INSERT INTO Casting (id,character,actor_id,movie_id) " + "SELECT (" + newId + ", '" + character + "','" + actorId + "','" + movieId + "');\nDROP RULE \"Casting_on_duplicate_ignore\" ON Casting";

        System.out.println("SQL => " + rule + sql);
        dbm.execute(rule + sql, "Casting", newId);
    }

}
