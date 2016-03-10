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
    public void login(String username, String password) throws ServerException {

    }

    @Override
    public void register(String username, String password) throws ServerException {

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
    public void joinGame(int id, CatanColor color) throws ServerException {

    }

    @Override
    public CatanModel getGameModel(AuthToken token) throws ServerException {
        return null;
    }

    @Override
    public void addAI(AuthToken token) throws ServerException {

    }

    @Override
    public String[] listAI(AuthToken token) throws ServerException {
        return new String[0];
    }

    @Override
    public Object executeCommand(ICommand command) throws ServerException {
        return null;
    }
}
