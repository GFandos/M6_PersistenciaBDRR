/**
 * Created by Gerard on 11/12/2016.
 */
public class Actor {

    private String name;
    private int id;
    private String character;

    public Actor(String _name, int _id) {
        name = _name;
        id = _id;
        character = "";
    }

    public void insertToDatabase(DatabaseManager dbm) {

        String rule = "CREATE RULE \"Actor_on_duplicate_ignore\" AS ON INSERT TO \"Actor\" WHERE EXISTS(SELECT 1 FROM Actor WHERE (id = NEW.id)) DO INSTEAD NOTHING;\n";

        String sql = "INSERT INTO Actor (id,name) " + "VALUES (" + id + ", '" + name + "') WHERE NOT EXISTS (SELECT 1 FROM Actor WHERE id='" + id + "');\nDROP RULE \"Actor_on_duplicate_ignore\" ON Actor";

        System.out.println("SQL => " + rule + sql);
        dbm.execute(rule + sql, "Actor", id);
    }

    public void setCharacter(String c) {
        character = c;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCharacter() {
        return character;
    }
}
