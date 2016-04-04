package server.database.sql;

import model.CatanModel;
import server.database.IGameDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLGameDAO implements IGameDAO {
    @Override
    public void addGame(CatanModel game) throws DatabaseException {

    }

    @Override
    public Object getGame(int gameID) throws DatabaseException {
        return null;
    }

    @Override
    public Object[] getAllGames() throws DatabaseException {
        return new Object[0];
    }

    @Override
    public void updateGame(int gameID) throws DatabaseException {

    }

    @Override
    public void deleteGame(int gameID) throws DatabaseException {

    }
}
