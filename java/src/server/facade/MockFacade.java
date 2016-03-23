package server.facade;

import client.data.GameInfo;
import com.google.inject.Inject;
import model.CatanModel;
import model.players.Player;
import server.AuthToken;
import server.command.ICommand;
import server.data.UserData;
import server.database.IDatabase;
import server.exception.*;
import serverProxy.JSONDeserializer;
import shared.definitions.CatanColor;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Joshua on 3/9/2016.
 */
public class MockFacade implements IServerFacade
{
    private final IDatabase database;

    @Inject
    public MockFacade(IDatabase database)
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
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException
    {
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

        //Player Joined Previously
        if (model.playerManager.containsId(token.getPlayerID()))
        {
            System.out.println("Player " + token.getName() + " is already part of this game" );
            for(Player player : model.getPlayerManager().getCatanPlayers())
            {
                if(player != null && player.getName().equals(token.getName()))
                    player.setColor(color);
            }
            // return the information that "playerID" needs on his client to start playing
            return "catan.game=" + gameId + ";Path=/;";
        }

        //Case where the game is full
        if (model.playerManager.getInitializedPlayerCount() >=4)
            throw new UnauthorizedException("Join Game is already full.");

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
    public void saveGame(int gameId, String fileName) throws ServerException
    {

    }

    @Override
    public void loadGame(String fileName) throws ServerException
    {

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

    }
}
