package plugin.sql;

import model.CatanModel;
import server.database.GameData;
import server.database.IGameDAO;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLGameDAO implements IGameDAO
{
    private final SQLPlugin database;

    public SQLGameDAO(SQLPlugin database)
    {
        this.database = database;
    }

    @Override
    public void addGame(CatanModel game) throws DatabaseException
    {

    }

    @Override
    public GameData getGame(int gameID) throws DatabaseException {
        return null;
    }

    @Override
    public GameData[] getAllGames() throws DatabaseException {
        return new GameData[0];
    }

    @Override
    public void updateGame(int gameID) throws DatabaseException {

    }

    @Override
    public void deleteGame(int gameID) throws DatabaseException {

    }
}
