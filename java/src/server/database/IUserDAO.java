package server.database;

import server.data.UserData;
import server.exception.DatabaseException;

/**
 * Abstract interface any UserDAO must implement in order to be plugged in
 */
public interface IUserDAO
{
    /**
     * Adds an already created user to the database
     * @param user
     * @throws DatabaseException
     */
    void addUser(UserData user) throws DatabaseException;

    /**
     * Returns user object for a game stored at unique ID
     * @param userID Unique ID of the game to be returned
     * @return Object representing game data stored in the database
     * @throws DatabaseException
     */
    UserData getUser(int userID) throws DatabaseException;

    /**
     * Returns all user data currently stored in the database
     * @return An array of all objects representing game data stored in the database
     * @throws DatabaseException
     */
    UserData[] getAllUsers() throws DatabaseException;

    /**
     * Delete the data stored in the database representing a specific user
     * @param userID Unique ID of game to be deleted
     * @throws DatabaseException
     */
    void deleteUser(int userID) throws DatabaseException;
}
