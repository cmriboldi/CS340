package app.database;

import app.client.GameInfo;
import app.command.ICommand;
import app.model.CatanModel;
import app.server.UserData;

/**
 * Created by Joshua on 3/9/2016.
 */
public interface IDatabase
{
    void addUser(UserData user);

    void addGame(String name, CatanModel game);

    void addCommand(int gameID, ICommand command);

    GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);

    UserData getUserById(int id);

    UserData getUserByName(String name);

    CatanModel getGameModel(int gameId);

    void updateGameModel(int gameId, CatanModel model);

    void deleteGameModel(int gameId);

    GameInfo[] listGames();
}
