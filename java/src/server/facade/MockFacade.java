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
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;

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
        try
        {
            return JSONDeserializer.deserialize("{\"bank\":{\"brick\":16,\"ore\":17,\"sheep\":13,\"wheat\":14,\"wood\":14},\"deck\":{\"monopoly\":2,\"monument\":5,\"roadBuilding\":2,\"soldier\":14,\"yearOfPlenty\":2},\"chat\":{\"lines\":[]},\"log\":{\"lines\":[{\"message\":\"Pete built a settlement.\",\"source\":\"Pete\"},{\"message\":\"Pete built a road.\",\"source\":\"Pete\"},{\"message\":\"Sam built a settlement.\",\"source\":\"Sam\"},{\"message\":\"Sam built a road.\",\"source\":\"Sam\"},{\"message\":\"Brooke built a settlement.\",\"source\":\"Brooke\"},{\"message\":\"Brooke built a road.\",\"source\":\"Brooke\"},{\"message\":\"Mark built a settlement.\",\"source\":\"Mark\"},{\"message\":\"Mark built a road.\",\"source\":\"Mark\"},{\"message\":\"Mark built a settlement.\",\"source\":\"Mark\"},{\"message\":\"Mark built a road.\",\"source\":\"Mark\"},{\"message\":\"Brooke built a settlement.\",\"source\":\"Brooke\"},{\"message\":\"Brooke built a road.\",\"source\":\"Brooke\"},{\"message\":\"Sam built a settlement.\",\"source\":\"Sam\"},{\"message\":\"Sam built a road.\",\"source\":\"Sam\"},{\"message\":\"Pete built a settlement.\",\"source\":\"Pete\"},{\"message\":\"Pete built a road.\",\"source\":\"Pete\"}]},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-1},\"resource\":\"wood\",\"number\":3},{\"location\":{\"x\":1,\"y\":0},\"resource\":\"brick\",\"number\":5},{\"location\":{\"x\":0,\"y\":0},\"resource\":\"wheat\",\"number\":11},{\"location\":{\"x\":1,\"y\":1},\"resource\":\"sheep\",\"number\":10},{\"location\":{\"x\":-1,\"y\":-1},\"resource\":\"brick\",\"number\":8},{\"location\":{\"x\":0,\"y\":1},\"resource\":\"wood\",\"number\":4},{\"location\":{\"x\":-1,\"y\":0},\"resource\":\"sheep\",\"number\":10},{\"location\":{\"x\":0,\"y\":2},\"resource\":\"wheat\",\"number\":8},{\"location\":{\"x\":-1,\"y\":1},\"resource\":\"sheep\",\"number\":9},{\"location\":{\"x\":-2,\"y\":0},\"resource\":\"ore\",\"number\":5},{\"location\":{\"x\":-1,\"y\":2},\"resource\":\"ore\",\"number\":3},{\"location\":{\"x\":-2,\"y\":1},\"resource\":\"wheat\",\"number\":3},{\"location\":{\"x\":-2,\"y\":2},\"resource\":\"wood\",\"number\":6},{\"location\":{\"x\":2,\"y\":-2},\"resource\":\"wood\",\"number\":11},{\"location\":{\"x\":1,\"y\":-2},\"resource\":\"brick\",\"number\":4},{\"location\":{\"x\":2,\"y\":-1},\"resource\":\"sheep\",\"number\":12},{\"location\":{\"x\":0,\"y\":-2},\"resource\":\"desert\",\"number\":-1},{\"location\":{\"x\":1,\"y\":-1},\"resource\":\"ore\",\"number\":9},{\"location\":{\"x\":2,\"y\":0},\"resource\":\"wheat\",\"number\":6}],\"ports\":[{\"location\":{\"x\":3,\"y\":-3},\"direction\":\"SW\",\"ratio\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-3},\"direction\":\"S\",\"ratio\":2},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-2},\"direction\":\"S\",\"ratio\":2},{\"resource\":\"wood\",\"location\":{\"x\":-3,\"y\":2},\"direction\":\"NE\",\"ratio\":2},{\"resource\":\"brick\",\"location\":{\"x\":-2,\"y\":3},\"direction\":\"NE\",\"ratio\":2},{\"location\":{\"x\":0,\"y\":3},\"direction\":\"N\",\"ratio\":3},{\"location\":{\"x\":-3,\"y\":0},\"direction\":\"SE\",\"ratio\":3},{\"resource\":\"sheep\",\"location\":{\"x\":3,\"y\":-1},\"direction\":\"NW\",\"ratio\":2},{\"location\":{\"x\":2,\"y\":1},\"direction\":\"NW\",\"ratio\":3}],\"roads\":[{\"owner\":1,\"location\":{\"x\":0,\"y\":1,\"direction\":\"NW\"}},{\"owner\":0,\"location\":{\"x\":1,\"y\":2,\"direction\":\"NW\"}},{\"owner\":0,\"location\":{\"x\":0,\"y\":3,\"direction\":\"N\"}},{\"owner\":1,\"location\":{\"x\":-1,\"y\":2,\"direction\":\"N\"}},{\"owner\":2,\"location\":{\"x\":1,\"y\":1,\"direction\":\"N\"}},{\"owner\":3,\"location\":{\"x\":0,\"y\":0,\"direction\":\"N\"}},{\"owner\":3,\"location\":{\"x\":1,\"y\":-1,\"direction\":\"NW\"}},{\"owner\":2,\"location\":{\"x\":2,\"y\":0,\"direction\":\"NW\"}}],\"settlements\":[{\"owner\":3,\"location\":{\"x\":1,\"y\":-1,\"direction\":\"NW\"}},{\"owner\":2,\"location\":{\"x\":2,\"y\":0,\"direction\":\"NW\"}},{\"owner\":2,\"location\":{\"x\":1,\"y\":1,\"direction\":\"NW\"}},{\"owner\":3,\"location\":{\"x\":0,\"y\":0,\"direction\":\"NW\"}},{\"owner\":1,\"location\":{\"x\":0,\"y\":1,\"direction\":\"NW\"}},{\"owner\":0,\"location\":{\"x\":1,\"y\":2,\"direction\":\"NW\"}},{\"owner\":0,\"location\":{\"x\":0,\"y\":3,\"direction\":\"NW\"}},{\"owner\":1,\"location\":{\"x\":-1,\"y\":2,\"direction\":\"NW\"}}],\"cities\":[],\"radius\":3,\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"cities\":4,\"color\":\"red\",\"discarded\":false,\"monuments\":0,\"name\":\"Pete\",\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"oldDevCards\":{\"monopoly\":1,\"monument\":1,\"roadBuilding\":1,\"soldier\":1,\"yearOfPlenty\":1},\"playerIndex\":0,\"playedDevCard\":false,\"playerID\":1,\"resources\":{\"brick\":10,\"ore\":10,\"sheep\":10,\"wheat\":10,\"wood\":10},\"roads\":13,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2},{\"cities\":4,\"color\":\"orange\",\"discarded\":false,\"monuments\":0,\"name\":\"Sam\",\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"playerIndex\":1,\"playedDevCard\":false,\"playerID\":2,\"resources\":{\"brick\":0,\"ore\":1,\"sheep\":2,\"wheat\":1,\"wood\":2},\"roads\":13,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2},{\"cities\":4,\"color\":\"blue\",\"discarded\":false,\"monuments\":0,\"name\":\"Brooke\",\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"playerIndex\":2,\"playedDevCard\":false,\"playerID\":3,\"resources\":{\"brick\":2,\"ore\":0,\"sheep\":2,\"wheat\":1,\"wood\":1},\"roads\":13,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2},{\"cities\":4,\"color\":\"green\",\"discarded\":false,\"monuments\":0,\"name\":\"Mark\",\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0},\"playerIndex\":3,\"playedDevCard\":false,\"playerID\":4,\"resources\":{\"brick\":1,\"ore\":1,\"sheep\":1,\"wheat\":1,\"wood\":2},\"roads\":13,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2}],\"turnTracker\":{\"currentTurn\":0,\"status\":\"Rolling\",\"longestRoad\":-1,\"largestArmy\":-1},\"version\":16,\"winner\":-1}");
        }
        catch (TurnIndexException e)
        {
            e.printStackTrace();
        }
        catch (InvalidTurnStatusException e)
        {
            e.printStackTrace();
        }
        catch (GeneralPlayerException e)
        {
            e.printStackTrace();
        }
        return null;
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
