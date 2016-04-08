package test;

import model.CatanModel;
import plugin.sql.SQLPlugin;
import server.database.GameData;
import server.database.IPersistencePlugin;
import server.database.VolatileDatabase;
import server.exception.DatabaseException;
import server.facade.ServerFacade;

/**
 * Created by Joshua on 4/4/2016.
 */
public class TestSQLMain
{
    public static void main(String[] args)
    {
        IPersistencePlugin plugin = new SQLPlugin(new ServerFacade(new VolatileDatabase()));
        System.out.println("Got this far");
        try
        {
            //plugin.clear();
            plugin.startTransaction();
            plugin.getGameDAO().addGame(new GameData(0, "Test", new CatanModel(false, false, false)));
            //plugin.getGameDAO().deleteGame(2);
            //plugin.getGameDAO().updateGame(1);
            plugin.endTransaction(true);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
