package test;

import model.CatanModel;
import plugin.IPluginData;
import plugin.PluginData;
import plugin.sql.SQLPlugin;
import server.AuthToken;
import server.command.MonopolyCommand;
import server.data.UserData;
import server.database.GameData;
import plugin.IPersistencePlugin;
import server.database.VolatileDatabase;
import server.exception.DatabaseException;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import shared.communication.JSON.MonopolyJSON;

/**
 * Created by Joshua on 4/4/2016.
 */
public class TestSQLMain
{
    public static void main(String[] args)
    {
        IServerFacade facade = new ServerFacade(new VolatileDatabase());

        IPluginData plugData = new PluginData();
        plugData.setCheckinSize(10);

        IPersistencePlugin plugin = new SQLPlugin(facade, plugData);
        System.out.println("Got this far");
        try
        {
            facade.createGame(true, true, true, "Test 2");
//            plugin.clear();
            plugin.startTransaction();
//            plugin.getGameDAO().addGame(new GameData(0, "Test", new CatanModel(false, false, false)));
//            plugin.getGameDAO().deleteGame(2);
//            plugin.getGameDAO().updateGame(0);
            for(int i = 0; i < 10; i++)
            {
//                plugin.getGameDAO().addGame(new GameData(i, "Test", new CatanModel(true, true, true)));
            }
            GameData[] games = plugin.getGameDAO().getAllGames();
            for(GameData game : games)
            {
                System.out.println(game.getGameID());
            }

            // Test commands
//            plugin.getUserDAO().addUser(new UserData("test1", "password"));
            UserData user = plugin.getUserDAO().getUser(0);
            System.out.println(user.getName());

            // Test commands
            AuthToken token = new AuthToken("string", "string", 0, 0);
//            plugin.getCommandDAO().addCommand(new MonopolyCommand(token, new MonopolyJSON(0, "WOOD"), facade));

            plugin.endTransaction(true);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }
    }
}
