package server.database.sql;

import client.data.GameInfo;
import model.CatanModel;
import server.command.ICommand;
import server.data.UserData;
import server.database.*;
import server.exception.DatabaseException;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLDatabaseProxy implements IDatabase, IPersistencePlugin {
    @Override
    public void addUser(UserData user) {

    }

    @Override
    public void addGame(String name, CatanModel game) {

    }

    @Override
    public void addCommand(int gameID, ICommand command) {

    }

    @Override
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
        return null;
    }

    @Override
    public UserData getUserById(int id) {
        return null;
    }

    @Override
    public UserData getUserByName(String name) {
        return null;
    }

    @Override
    public CatanModel getGameModel(int gameId) {
        return null;
    }

    @Override
    public void updateGameModel(int gameId, CatanModel model) {

    }

    @Override
    public void deleteGameModel(int gameId) {

    }

    @Override
    public GameInfo[] listGames() {
        return new GameInfo[0];
    }

    @Override
    public void startTransaction() throws DatabaseException {

    }

    @Override
    public void stopTransaction() throws DatabaseException {

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
