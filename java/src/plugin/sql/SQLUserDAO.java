package plugin.sql;

import server.data.UserData;
import server.database.IUserDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLUserDAO implements IUserDAO
{
    private final SQLPlugin database;

    public SQLUserDAO(SQLPlugin database)
    {
        this.database = database;
    }

    @Override
    public void addUser(UserData user) throws DatabaseException {

    }

    @Override
    public UserData getUser(int userID) throws DatabaseException {
        return null;
    }

    @Override
    public UserData[] getAllUsers() throws DatabaseException {
        return new UserData[0];
    }

    @Override
    public void deleteUser(int userID) throws DatabaseException {

    }
}
