package server.database;

import client.data.GameInfo;
import model.CatanModel;
import server.data.UserData;

/**
 * Created by Joshua on 3/9/2016.
 */
public interface IDatabase
{
    void addUser(UserData user);

    void addGame(String name, CatanModel game);

    UserData getUserById(int id);

    UserData getUserByName(String name);

    CatanModel getGameModel(int gameId);

    void updateGameModel(int gameId, CatanModel model);

    void deleteGameModel(int gameId);

    GameInfo[] listGames();
}
