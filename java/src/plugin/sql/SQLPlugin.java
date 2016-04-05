package plugin.sql;

import com.google.inject.Inject;
import server.database.ICommandDAO;
import server.database.IGameDAO;
import server.database.IPersistencePlugin;
import server.database.IUserDAO;
import server.exception.DatabaseException;
import server.facade.IServerFacade;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Joshua on 4/4/2016.
 */
public class SQLPlugin implements IPersistencePlugin
{
    private static final String DATABASE_DIRECTORY = "plugins/sql/database";
    private static final String DATABASE_FILE = "database.sqlite";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
            File.separator + DATABASE_FILE;

    public static void initialize() throws DatabaseException
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            File newFile = new File(DATABASE_DIRECTORY + File.separator + DATABASE_FILE);
            if(!newFile.getParentFile().getParentFile().getParentFile().exists())
            {
                newFile.getParentFile().getParentFile().getParentFile().mkdir();
                newFile.getParentFile().getParentFile().mkdir();
                newFile.getParentFile().mkdir();
            }

            FileWriter writer = new FileWriter(newFile, false);
            writer.write("stuff");
            writer.flush();
            writer.close();
        }
        catch(ClassNotFoundException e)
        {
            throw new DatabaseException("Loading JDBC: class not found");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private final IServerFacade facade;
    private SQLUserDAO userDAO;
    private SQLGameDAO gameDAO;
    private SQLCommandDAO commandDAO;
    private Connection connection;

    @Inject
    public SQLPlugin(IServerFacade facade)
    {
        this.facade = facade;
        userDAO = new SQLUserDAO(this);
        gameDAO = new SQLGameDAO(this);
        commandDAO = new SQLCommandDAO(this);
        connection = null;
        try
        {
            SQLPlugin.initialize();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void startTransaction() throws DatabaseException
    {

    }

    @Override
    public void stopTransaction() throws DatabaseException
    {

    }

    @Override
    public IGameDAO getGameDAO() throws DatabaseException
    {
        return gameDAO;
    }

    @Override
    public IUserDAO getUserDAO() throws DatabaseException
    {
        return userDAO;
    }

    @Override
    public ICommandDAO getCommandDAO() throws DatabaseException
    {
        return commandDAO;
    }
}
