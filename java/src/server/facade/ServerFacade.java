package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import model.players.Player;
import model.players.PlayerTurnTracker;
import server.AuthToken;
import server.command.ICommand;
import server.data.UserInfo;
import server.database.IDatabase;
import server.exception.*;
import shared.definitions.CatanColor;

import java.io.SyncFailedException;
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
    	System.out.println("Logging in (username): " + username + " (password): " + password); 
        if(!isValidUser(new AuthToken(username, password, -1, -1)))
            throw new InvalidCredentialsException("Login attempt invalid");

        UserInfo user = database.getUserByName(username);
        try
        {
            String userCookie = "catan.user=" + URLEncoder.encode(user.toJSON(), "UTF-8") + ";Path=/;";
            return userCookie;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            throw new InternalErrorException("UnsupportedEncodingException from URLEncoder");
        }
    }

    @Override
    public String register(String username, String password) throws ServerException
    {
    	System.out.println("Registering (username): " + username + " (password): " + password); 
        UserInfo user = new UserInfo(username, password);
        database.addUser(user);
        try
        {
            String userCookie = "catan.user=" + URLEncoder.encode(user.toJSON(), "UTF-8") + ";Path=/;";
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
    public String joinGame(AuthToken token, int gameId, CatanColor color) throws ServerException
    {
        if(!isValidUser(token))
            throw new UnauthorizedException("Join Game attempt is not authorized");
        System.out.println("In ServerFacade.joinGame()");

//        System.out.println("AuthToken:\nName: " + token.getName() + "\nPassword: " + token.getPassword() + "\nPlayerID: " + token.getPlayerID() + "\nGameID: " + token.getGameID());
        
        CatanModel model = database.getGameModel(gameId);
        PlayerTurnTracker turnTracker = model.getPlayerManager().getTurnTracker();

        
        // Game full and playerID not in list of current players
        if (!model.playerManager.containsId(token.getPlayerID()) && model.playerManager.getInitializedPlayerCount() >=4)
        {
        	System.out.println("Join Game is already full and player " + token.getPlayerID() + " is not listed as a current player");
        	// do nothing because "playerID" can't join this game
        	return "";
        }

        //Player Joined Previously
        if (model.playerManager.containsId(token.getPlayerID()))
        {
        	System.out.println("Player " + token.getPlayerID() + " is already part of this game" );
        	// return the information that "playerID" needs on his client to start playing
        	return "";
        }

        //Player can Join game first time
        if(!model.playerManager.containsId(token.getPlayerID()) && model.playerManager.getInitializedPlayerCount() < 4)
        {
        	int playerIndex = model.playerManager.getInitializedPlayerCount();
        	Player newPlayer = new Player(token.getName(),token.getPlayerID(), color, playerIndex);
        	model.playerManager.catanPlayers[playerIndex] = newPlayer;
        }


       if (model.playerManager.getCatanPlayers().length >=4)
    	   throw new UnauthorizedException("Join Game is already full");
        ////////////////////////////////////////////////////////////////////////////////////////
        //          Logic to add player to the Catan Model
        ////////////////////////////////////////////////////////////////////////////////////////
        return "catan.game=" + gameId + ";Path=/;";
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

    @Override
    public boolean isValidUser(AuthToken token)
    {
        if(database.getUserByName(token.getName()) == null)
        {
            return false;
        }
        if(database.getUserByName(token.getName()).getPassword().equals(token.getPassword()))
        {
            return true;
        }
        return false;
    }
}
