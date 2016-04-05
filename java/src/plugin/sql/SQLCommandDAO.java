package plugin.sql;

import server.command.ICommand;
import server.database.ICommandDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLCommandDAO implements ICommandDAO
{
    private final SQLPlugin database;

    public SQLCommandDAO(SQLPlugin database)
    {
        this.database = database;
    }


    @Override
    public void addCommand(ICommand command) throws DatabaseException {

    }

    @Override
    public ICommand getCommand(int commandID) throws DatabaseException {
        return null;
    }

    @Override
    public ICommand[] getAllCommands(int gameID) throws DatabaseException {
        return new ICommand[0];
    }

    @Override
    public ICommand[] getAllCommands(int gameID, int index) throws DatabaseException {
        return new ICommand[0];
    }

    @Override
    public void deleteCommand(int commandID) throws DatabaseException {

    }
}
