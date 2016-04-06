package server.database;

import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public interface IPersistencePlugin
{
    /**
     * Begin a transaction with the persistent database
     * @throws DatabaseException
     */
    void startTransaction() throws DatabaseException;

    /**
     * End the current transaction with the persistent database
     * @throws DatabaseException
     */
    void stopTransaction() throws DatabaseException;
    
    /**
     * Load all the pertinent data contained in the database into memory.
     * @throws DatabaseException
     */
    void thaw() throws DatabaseException;

    /**
     * Get the DAO used to access game data in the persistent database
     * @return IGameDAO object used to access game data
     * @throws DatabaseException
     */
    IGameDAO getGameDAO() throws DatabaseException;

    /**
     * Get the DAO used to access user data in the persistent database
     * @return IUserDAO used to access user data
     * @throws DatabaseException
     */
    IUserDAO getUserDAO() throws DatabaseException;

    /**
     * Get the DAO used to access the command data in the persistent database
     * @return ICommandDAO used to access command data
     * @throws DatabaseException
     */
    ICommandDAO getCommandDAO() throws DatabaseException;
}
