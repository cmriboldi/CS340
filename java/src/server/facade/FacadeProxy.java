package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.exception.FacadeNotInitializedException;
import server.exception.ServerException;
import shared.definitions.CatanColor;

/**
 * Created by Joshua on 3/10/2016.
 */
public class FacadeProxy
{
    private static FacadeProxy _instance;
    private static IServerFacade facade;

    private FacadeProxy(IServerFacade facade)
    {
        this.facade = facade;
    }

    private static FacadeProxy instance() throws FacadeNotInitializedException
    {
        if(_instance == null)
            throw new FacadeNotInitializedException("Server Facade has not been initialized");
        return _instance;
    }

    public static void setFacade(IServerFacade facade)
    {
        _instance = new FacadeProxy(facade);
    }

    public static void login(String username, String password) throws ServerException
    {
        instance().facade.login(username, password);
    }

    public static void register(String username, String password) throws ServerException
    {
        instance().facade.register(username, password);
    }

    public static GameInfo[] listGames() throws ServerException
    {
        return instance().facade.listGames();
    }

    public static GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException
    {
        return instance().facade.createGame(randomTiles, randomNumbers, randomPorts, name);
    }

    public static void joinGame(AuthToken token, CatanColor color) throws ServerException
    {
        instance().facade.joinGame(token, color);
    }

    public static void saveGame(int gameId, String fileName) throws ServerException
    {
        instance().facade.saveGame(gameId, fileName);
    }

    public  static void loadGame(String fileName) throws ServerException
    {
        instance().facade.loadGame(fileName);
    }

    public static CatanModel getGameModel(AuthToken token) throws ServerException
    {
        return instance().facade.getGameModel(token);
    }

    public void addAI(AuthToken token) throws ServerException
    {
        instance().facade.addAI(token);
    }

    public String[] listAI(AuthToken token) throws ServerException
    {
        return instance().facade.listAI(token);
    }
}
