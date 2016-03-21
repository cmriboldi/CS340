package server.facade;

import client.data.GameInfo;
import client.data.PlayerInfo;
import com.google.inject.Inject;
import model.CatanModel;
import model.players.Player;
import model.players.PlayerTurnTracker;
import server.AuthToken;
import server.command.ICommand;
import server.data.UserData;
import server.database.IDatabase;
import server.exception.*;
import shared.definitions.CatanColor;

import java.io.SyncFailedException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
    private final IDatabase database;

    /**
     * To allow for dependency injection, when constructed the database that will be used should be passed in
     * @param database The IDatabase that will be used for this build
     */
    @Inject
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

        UserData user = database.getUserByName(username);
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
        if(database.getUserByName(username) != null)
            throw new BadRequestException("There is already a registered user with that username");

        UserData user = new UserData(username, password);
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
    public GameInfo[] listGames() throws ServerException
    {
        return database.listGames();
    }

    @Override
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException {
        
    	System.out.println("[server facade ] Creating Game");
    	return database.createGame(randomTiles, randomNumbers, randomPorts, name);
    }

    @Override
    public String joinGame(AuthToken token, int gameId, CatanColor color) throws ServerException
    {
        if(!isValidUser(token))
        {
            throw new InvalidCredentialsException("Join Game attempt is not authorized");
        }

        CatanModel model = database.getGameModel(gameId);
        
        // Game full and playerID not in list of current players
        if (!model.playerManager.containsId(token.getPlayerID()) && model.playerManager.getInitializedPlayerCount() >=4)
        {
        	System.out.println("Join Game is already full and player " + token.getPlayerID() + " is not listed as a current player");
        	// do nothing because "playerID" can't join this game
        	return "";
        }

        //Case where the game is full
        if (model.playerManager.getInitializedPlayerCount() >=4)
            throw new UnauthorizedException("Join Game is already full");
        
        //Player Joined Previously
        if (model.playerManager.containsId(token.getPlayerID()))
        {
        	System.out.println("Player " + token.getPlayerID() + " is already part of this game" );
        	// return the information that "playerID" needs on his client to start playing
        	return "catan.game=" + gameId + ";Path=/;";
        }
        
        //Player can Join game first time
        if(!model.playerManager.containsId(token.getPlayerID()) && model.playerManager.getInitializedPlayerCount() < 4)
        {
        	System.out.println("Joining game for first time!"); 
        	int playerIndex = model.playerManager.getInitializedPlayerCount();
        	Player newPlayer = new Player(token.getName(),token.getPlayerID(), color, playerIndex);
        	model.playerManager.catanPlayers[playerIndex] = newPlayer;
        }



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
    public CatanModel getGameModel(AuthToken token) throws ServerException
    {
        return database.getGameModel(token.getGameID());
    }

    @Override
    public Object getGameModel(AuthToken token, int version) throws ServerException
    {
        CatanModel game = database.getGameModel(token.getGameID());
        if(version == game.getVersion())
            return true;
        return game;
    }

    @Override
    public void updateGame(AuthToken token, CatanModel model) throws ServerException
    {
        database.updateGameModel(token.getGameID(), model);
    }

    @Override
    public void addAI(AuthToken token) throws ServerException {

    }

    @Override
    public String[] listAI(AuthToken token) throws ServerException 
    {
    	String[] list = {"No AIs"};
        return list;
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

    @Override
    public void recordCommand(AuthToken token, ICommand command) throws ServerException
    {
        database.addCommand(token.getGameID(), command);
    }
}
