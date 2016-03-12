package server.database;

import model.CatanModel;
import server.data.UserInfo;

/**
 * Created by Joshua on 3/9/2016.
 */
public interface IDatabase
{
    void addUser(UserInfo user);

    void addGame(CatanModel game);

    UserInfo getUserById(int id);

    UserInfo getUserByName(String name);

    CatanModel getGameModel(int gameId);

    void updateGameModel(int gameId, CatanModel model);

    void deleteGameModel(int gameId);
}
