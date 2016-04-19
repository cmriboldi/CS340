package plugin;

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
    ICommand getCommand(int commandID) throws DatabaseException;

    /**
     * Returns all command data currently stored in the database for a specific game in order of execution
     * @param gameID Unique ID of game to get all commands for
     * @return An array of all objects representing game data stored in the database
     * @throws DatabaseException
     */
    ICommand[] getAllCommands(int gameID) throws DatabaseException;

    /**
     * Delete the data stored in the database representing a specific command
     * @param commandID Unique ID of command to be deleted
     * @throws DatabaseException
     */
    void deleteCommand(int commandID) throws DatabaseException;
}
