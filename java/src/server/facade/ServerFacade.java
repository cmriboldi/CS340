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
    private IDatabase database;

    public ServerFacade(IDatabase database)
    {
    	this.database = database;
    }

    @Override
    public void login(String username, String password) throws ServerException
    {
        System.out.println("I am in the server facade and printing this lovely comment for you");

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
    public void joinGame(AuthToken token, CatanColor color) throws ServerException {

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
    public void addAI(AuthToken token) throws ServerException {

    }

    @Override
    public String[] listAI(AuthToken token) throws ServerException {
        return new String[0];
    }
}
