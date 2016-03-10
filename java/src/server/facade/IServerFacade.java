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
public interface IServerFacade
{
    void login(String username, String password) throws ServerException;

    void register(String username, String password) throws ServerException;

    GameInfo[] listGames() throws ServerException;

    GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException;

    void joinGame(AuthToken token, int id, CatanColor color) throws ServerException;

    CatanModel getGameModel(AuthToken token) throws ServerException;

    void addAI(AuthToken token) throws ServerException;

    String[] listAI(AuthToken token) throws ServerException;

    Object executeCommand(ICommand command) throws ServerException;
}
