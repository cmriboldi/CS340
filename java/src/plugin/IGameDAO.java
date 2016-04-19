package plugin;

import model.CatanModel;
import server.database.GameData;
import server.exception.DatabaseException;

/**
 * Abstract interface any GameDAO must implement in order to be plugged in
 */
public interface IGameDAO
{
    /**
     * Adds an already created game to the database
     * @param game
     * @throws DatabaseException
     */
    void addGame(GameData game) throws DatabaseException;

    /**
     * Returns game object for a game stored at unique ID
     * @param gameID Unique ID of the game to be returned
     * @return Object representing game data stored in the database
     * @throws DatabaseException
     */
    GameData getGame(int gameID) throws DatabaseException;

    /**
     * Returns all game data currently stored in the database
     * @return An array of all objects representing game data stored in the database
     * @throws DatabaseException
     */
    GameData[] getAllGames() throws DatabaseException;

    /**
     * Update the data stored in the database to reflect the command objects also stored in the database
     * @param gameID Unique ID of the game to be returned
     * @throws DatabaseException
     */
    void updateGame(int gameID) throws DatabaseException;

    /**
     * Delete the data stored in the database representing a specific game
     * @param gameID Unique ID of game to be deleted
     * @throws DatabaseException
     */
    void deleteGame(int gameID) throws DatabaseException;
}
