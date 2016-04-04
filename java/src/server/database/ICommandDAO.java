package server.database;

import server.command.ICommand;
import server.data.UserData;
import server.exception.DatabaseException;

/**
 * Abstract interface any CommandDAO must implement in order to be plugged in
 */
public interface ICommandDAO
{
    /**
     * Adds an already created command to the database
     * @param command
     * @throws DatabaseException
     */
    void addCommand(ICommand command) throws DatabaseException;

    /**
     * Returns command object for a command stored at unique ID
     * @param commandID Unique ID of the game to be returned
     * @return Object representing game data stored in the database
     * @throws DatabaseException
     */
    Object getCommand(int commandID) throws DatabaseException;

    /**
     * Returns all command data currently stored in the database for a specific game in order of execution
     * @param gameID Unique ID of game to get all commands for
     * @return An array of all objects representing game data stored in the database
     * @throws DatabaseException
     */
    Object[] getAllCommands(int gameID) throws DatabaseException;

    /**
     * Returns all command data currently stored in the database for a specific game in order of execution
     * from a specific index on
     * @param gameID Unique ID of game to get all commands for
     * @param index Order of execution of a particular command on a game
     * @return All command objects that occurred at the index and after
     * @throws DatabaseException
     */
    Object[] getAllCommands(int gameID, int index) throws DatabaseException;

    /**
     * Delete the data stored in the database representing a specific command
     * @param commandID Unique ID of command to be deleted
     * @throws DatabaseException
     */
    void deleteCommand(int commandID) throws DatabaseException;
}
