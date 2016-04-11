package plugin.sql;

/**
 * Created by Joshua on 4/7/2016.
 */
public final class SQLQuery
{
    private SQLQuery(){}

    public static String createGameTable()
    {
        return "CREATE TABLE game\n" +
                "(\n" +
                "id INTEGER PRIMARY KEY,\n" +
                "game_name VARCHAR(40),\n" +
                "version INT NOT NULL,\n" +
                "model BLOB NOT NULL\n" +
                ");";
    }

    public static String createUserTable()
    {
        return "CREATE TABLE users\n" +
                "(\n" +
                "id INTEGER PRIMARY KEY,\n" +
                "user_name TEXT NOT NULL,\n" +
                "user_password TEXT NOT NULL\n" +
                ");";
    }

    public static String createCommandTable()
    {
        return "CREATE TABLE command\n" +
                "(\n" +
                "id INTEGER PRIMARY KEY,\n" +
                "game_id INT NOT NULL,\n" +
                "command_type TEXT NOT NULL,\n" +
                "auth_token BLOB NOT NULL,\n" +
                "json BLOB NOT NULL\n" +
                ");";
    }

    public static String getGame(int gameID)
    {
        return "SELECT * FROM game WHERE id = " + gameID + ";";
    }

    public static String getAllGames()
    {
        return "SELECT id, game_name, model FROM game ORDER BY id;";
    }

    public static String addGame()
    {
        return "INSERT INTO game(game_name, version, model) VALUES(?,?,?);";
    }

    public static String updateGame()
    {
        return "UPDATE game\n" +
                "SET version=?, model=?\n" +
                "WHERE id=?;";
    }

    public static String dropGameTable()
    {
        return "DROP TABLE game;";
    }

    public static String dropUsersTable()
    {
        return "DROP TABLE users;";
    }

    public static String dropCommandTable()
    {
        return "DROP TABLE command;";
    }

    public static String deleteGame(int gameID)
    {
        return "DELETE FROM game WHERE id=" + gameID + ";";
    }

    public static String deleteAllGames()
    {
        return "DELETE FROM game;";
    }

    public static String deleteAllUsers()
    {
        return "DELETE FROM users;";
    }

    public static String deleteAllCommands()
    {
        return "DELETE FROM command;";
    }

    public static String deleteAllCommandsForGame(int gameID)
    {
        return "DELETE FROM command WHERE game_id=" + gameID + ";";
    }
}
