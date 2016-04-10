package plugin;

import server.database.ICommandDAO;
import server.database.IGameDAO;
import server.database.IUserDAO;
import server.exception.DatabaseException;

/**
 * Created by clayt on 4/8/2016.
 */
public class EmptyPlugin implements IPersistencePlugin {

    @Override
    public void startTransaction() throws DatabaseException {

    }

    @Override
    public void endTransaction(boolean commit) throws DatabaseException {

    }

    @Override
    public void clear() throws DatabaseException {

    }

    @Override
    public void thaw() throws DatabaseException {

    }

    @Override
    public IGameDAO getGameDAO() throws DatabaseException {
        return null;
    }

    @Override
    public IUserDAO getUserDAO() throws DatabaseException {
        return null;
    }

    @Override
    public ICommandDAO getCommandDAO() throws DatabaseException {
        return null;
    }
}
