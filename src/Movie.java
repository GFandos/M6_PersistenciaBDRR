/**
 * Created by Gerard on 11/12/2016.
 */
public class Movie {

    private String title;
    private String release_date;
    private int id;
    //ArrayList<Casting> casting;

    public Movie(String _title, String _release_date, int _id) {
        title = _title;
        release_date = _release_date;
        id = _id;
        //casting = _casting;
    }

    public void insertToDatabase(DatabaseManager dbm) {

        String rule = "CREATE RULE \"Movie_on_duplicate_ignore\" AS ON INSERT TO \"Movie\" WHERE EXISTS(SELECT 1 FROM Movie WHERE (id = NEW.id)) DO INSTEAD NOTHING;\n";

        String sql = "INSERT INTO Movie (id,title,release_date) " + "VALUES (" + id + ", '" + title + "','" + release_date + "');\nDROP RULE \"Movie_on_duplicate_ignore\" ON Movie";
        System.out.println("SQL => " + rule + sql);
        dbm.execute(rule + sql, "Movie", id);
    }

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

}
