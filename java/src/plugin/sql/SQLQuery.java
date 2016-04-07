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
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "game_name VARCHAR(40),\n" +
                "version INT NOT NULL,\n" +
                "model BLOB NOT NULL\n" +
                ");";
    }

    public static String createUserTable()
    {
        return "CREATE TABLE users\n" +
                "(\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "user_name TEXT NOT NULL,\n" +
                "user_password TEXT NOT NULL\n" +
                ");";
    }

    public static String createCommandTable()
    {
        return "CREATE TABLE command\n" +
                "(\n" +
                "command_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "game_id INT NOT NULL,\n" +
                "order_of_execution INT NOT NULL,\n" +
                "command BLOB NOT NULL\n" +
                ");";
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
}
