package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.command.ICommand;
import server.data.UserInfo;
import server.database.IDatabase;
import server.exception.FacadeNotInitializedException;
import server.exception.InternalErrorException;
import server.exception.ServerException;
import shared.definitions.CatanColor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    /**
     * To allow for dependency injection, when constructed the database that will be used should be passed in
     * @param database The IDatabase that will be used for this build
     */
    public ServerFacade(IDatabase database)
    {
    	this.database = database;
    }

    @Override
    public String login(String username, String password) throws ServerException
    {
        System.out.println("I am in the server facade and printing this lovely comment for you");
        return null;
    }

    @Override
    public String register(String username, String password) throws ServerException
    {
        UserInfo user = new UserInfo(username, password);
        database.addUser(user);
        try
        {
            String userCookie = URLEncoder.encode(user.toJSON(), "UTF-8");
            return userCookie;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new InternalErrorException("UnsupportedEncodingException from URLEncoder");
        }
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
