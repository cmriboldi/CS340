package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.command.ICommand;
import server.exception.ServerException;
import shared.definitions.CatanColor;

/**
 * Created by Joshua on 3/9/2016.
 */
public class MockFacade implements IServerFacade
{

    @Override
    public String login(String username, String password) throws ServerException {
        return null;
    }

    @Override
    public String register(String username, String password) throws ServerException
    {
        return null;
    }

    @Override
    public GameInfo[] listGames() throws ServerException {
        return new GameInfo[0];
    }

    @Override
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException {
        return null;
    }

    @Override
    public String joinGame(AuthToken token, int gameId, CatanColor color) throws ServerException {
        return null;
    }

    @Override
    public void saveGame(int gameId, String fileName) {

    }

    @Override
    public void loadGame(String fileName) {

    }

    @Override
    public CatanModel getGameModel(AuthToken token) throws ServerException {
        return null;
    }

    @Override
    public Object getGameModel(AuthToken token, int version) throws ServerException {
        return null;
    }

    @Override
    public void updateGame(AuthToken token, CatanModel model) throws ServerException {

    }

    @Override
    public void addAI(AuthToken token) throws ServerException {

    }

    @Override
    public String[] listAI(AuthToken token) throws ServerException {
        return new String[0];
    }

    @Override
    public boolean isValidUser(AuthToken token) throws ServerException {
        return false;
    }
}
