package server.database.sql;

import server.command.ICommand;
import server.database.ICommandDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLCommandDAO implements ICommandDAO {
    @Override
    public void addCommand(ICommand command) throws DatabaseException {

    }

    @Override
    public Object getCommand(int commandID) throws DatabaseException {
        return null;
    }

    @Override
    public Object[] getAllCommands(int gameID) throws DatabaseException {
        return new Object[0];
    }

    @Override
    public Object[] getAllCommands(int gameID, int index) throws DatabaseException {
        return new Object[0];
    }

    @Override
    public void deleteCommand(int commandID) throws DatabaseException {

    }
}
