package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.command.ICommand;
import server.database.IDatabase;
import server.exception.FacadeNotInitializedException;
import server.exception.ServerException;
import shared.definitions.CatanColor;

/**
 * Created by Joshua on 3/9/2016.
 */
public class ServerFacade implements IServerFacade
{
    private static ServerFacade _instance;
    private static IDatabase database;

    private ServerFacade(IDatabase database)
    {
        this.database = database;
    }

    public static ServerFacade get() throws ServerException {
        if(_instance == null)
            throw new FacadeNotInitializedException("Server Facade has not been initialized");
        return _instance;
    }

    public static void initialize(IDatabase database)
    {
        _instance = new ServerFacade(database);
    }

    private Object _executeCommand(ICommand command)
    {
        return command.execute();
    }

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
    public void joinGame(int id, CatanColor color) {

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
    public Object executeCommand(ICommand command) throws ServerException
    {
        return ServerFacade.get()._executeCommand(command);
    }

}
