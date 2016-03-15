package server.database;

import model.CatanModel;
import server.data.UserInfo;

import java.util.*;

/**
 * Created by Joshua on 3/10/2016.
 */
public class VolatileDatabase implements IDatabase
{
    private Map players;
    private Map playerKeys;
    private Map games;
    private int playerIndex;
    private int gameIndex;

    public VolatileDatabase()
    {
        players = new HashMap<Integer, UserInfo>();
        playerKeys = new HashMap<String, Integer>();
        games = new HashMap<Integer, CatanModel>();
        playerIndex = 0;
        gameIndex = 0;
        addGame(new CatanModel(0, "Default", true, true, true));
        addUser(new UserInfo("string", "string"));
    }

    @Override
    public void addUser(UserInfo user)
    {
        user.setId(playerIndex);
        players.put(playerIndex, user);
        playerKeys.put(user.getName(), playerIndex);
        playerIndex++;
    }

    @Override
    public void addGame(CatanModel game)
    {
        games.put(gameIndex, game);
        gameIndex++;
    }

    @Override
    public UserInfo getUserById(int id)
    {
        return (UserInfo) players.get(id);
    }

    @Override
    public UserInfo getUserByName(String name)
    {
        if(playerKeys.get(name) == null)
        {
            return null;
        }

        int key = (int)playerKeys.get(name);
        return (UserInfo) players.get(key);
    }

    @Override
    public CatanModel getGameModel(int gameId)
    {
        return (CatanModel) games.get(gameId);
    }

    @Override
    public void updateGameModel(int gameId, CatanModel model)
    {
        games.put(gameId, model);
    }

    @Override
    public void deleteGameModel(int gameId)
    {
        games.remove(gameId);
    }
}
