package test;

import plugin.sql.SQLPlugin;
import server.database.IPersistencePlugin;
import server.database.VolatileDatabase;
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
    }
}
