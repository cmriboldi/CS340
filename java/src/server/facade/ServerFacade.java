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
 * The ServerFacade class controls all interactions between the command classes, the handlers, and the database. 
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Mar, 2016.
 */
public class ServerFacade implements IServerFacade
{
    private static ServerFacade _instance;
    private IDatabase database;

    private ServerFacade(IDatabase database)
    {
    	this.database = database;
    }

    /**
     * This method is for accessing the single instance of the ServerFacade.
     * @return The instance of the singleton ServerFacade.
     * @throws FacadeNotInitializedException
     */
    public static ServerFacade instance() throws FacadeNotInitializedException {
        if(_instance == null)
            throw new FacadeNotInitializedException("Server Facade has not been initialized");
        return _instance;
    }

    /**
     * 
     * This method must be called before the ServerFacade is used. It must be called in order to set the database to be used by the ServerFacade.
     * 
     * @param database An IDatabase Object that will be used by the ServerFacade.
     */
    public static void initialize(IDatabase database) {
        _instance = new ServerFacade(database);
    }

    private Object _executeCommand(ICommand command) {
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
        return instance()._executeCommand(command);
    }

}
