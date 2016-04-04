package server.database.sql;

import server.data.UserData;
import server.database.IUserDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLUserDAO implements IUserDAO {
    @Override
    public void addUser(UserData user) throws DatabaseException {

    }

    @Override
    public Object getUser(int userID) throws DatabaseException {
        return null;
    }

    @Override
    public Object[] getAllUsers() throws DatabaseException {
        return new Object[0];
    }

    @Override
    public void deleteUser(int userID) throws DatabaseException {

    }
}
