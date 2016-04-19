
import command.ICommand;
import shared.communication.JSON.RollNumberJSON;
import database.GameData;
import server.exception.DatabaseException;
import server.exception.ServerException;
import plugin.*;
import server.facade.IServerFacade;
import server.UserData;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Joshua on 4/4/2016.
 */
public class SQLPlugin implements IPersistencePlugin
{
    private static final String DATABASE_DIRECTORY = "plugins/sql/database";
    private static final String DATABASE_FILE = "database.sqlite";
    private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
            File.separator + DATABASE_FILE;

    private void initialize() throws DatabaseException
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            File sqliteFile = new File(DATABASE_DIRECTORY + File.separator + DATABASE_FILE);
            if(!sqliteFile.getParentFile().getParentFile().getParentFile().exists())
                sqliteFile.getParentFile().getParentFile().getParentFile().mkdir();
            if(!sqliteFile.getParentFile().getParentFile().exists())
                sqliteFile.getParentFile().getParentFile().mkdir();
            if(!sqliteFile.getParentFile().exists())
                sqliteFile.getParentFile().mkdir();
            if(!sqliteFile.exists())
            {
                sqliteFile.createNewFile();
                startTransaction();
                Statement statement = getConnection().createStatement();
                statement.execute(SQLQuery.createGameTable());
                statement.execute(SQLQuery.createUserTable());
                statement.execute(SQLQuery.createCommandTable());
                endTransaction(true);
                statement.close();
            }
        }
        catch(ClassNotFoundException e)
        {
            throw new DatabaseException("Loading JDBC: class not found");
        }
        catch (IOException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        catch (SQLException e)
        {
            endTransaction(false);
            throw new DatabaseException(e.getMessage());
        }
    }

    private void safeClose(Connection conn) throws DatabaseException
    {
        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Error occurred while attempting to close the connection to the database");
            }
        }
    }

    private final IServerFacade facade;
    private SQLUserDAO userDAO;
    private SQLGameDAO gameDAO;
    private SQLCommandDAO commandDAO;
    private Connection connection;
    private int updateRate;

    public SQLPlugin(Object facade, Object plugData)
    {
        this.facade = (IServerFacade)facade;
        userDAO = new SQLUserDAO(this);
        gameDAO = new SQLGameDAO(this);
        commandDAO = new SQLCommandDAO(this);
        connection = null;
        updateRate = ((IPluginData)plugData).getCheckinSize();

        try
        {
            initialize();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    public int getUpdateRate()
    {
        return updateRate;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public IServerFacade getFacade()
    {
        return facade;
    }

    @Override
    public void startTransaction() throws DatabaseException
    {
        try
        {
            if(connection == null)
            {
                connection = DriverManager.getConnection(DATABASE_URL);
                connection.setAutoCommit(false);
            }
            else
                throw new DatabaseException("There is already an active connection");
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not connect to database");
        }
    }

    @Override
    public void endTransaction(boolean commit) throws DatabaseException
    {
        if (connection != null)
        {
            try
            {
                if (commit)
                {
                    connection.commit();
                }
                else
                {
                    connection.rollback();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            finally
            {
                safeClose(connection);
                connection = null;
            }
        }
    }

    @Override
    public void clear() throws DatabaseException
    {
        try
        {
            startTransaction();
            Statement statement = getConnection().createStatement();
            statement.execute(SQLQuery.dropGameTable());
            statement.execute(SQLQuery.dropUsersTable());
            statement.execute(SQLQuery.dropCommandTable());
            statement.execute(SQLQuery.createGameTable());
            statement.execute(SQLQuery.createUserTable());
            statement.execute(SQLQuery.createCommandTable());
            endTransaction(true);
            statement.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
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

	@Override
	public void thaw() throws DatabaseException
	{
        try
        {
            startTransaction();
            UserData[] users = userDAO.getAllUsers();
            for(UserData user : users)
            {
                facade.register(user.getName(), user.getPassword());
            }
            GameData[] games = gameDAO.getAllGames();
            int count = 0;
            for(GameData game : games)
            {
                facade.getDatabase().addGame(game.getName(), game.getModel());
                ICommand[] commands = commandDAO.getAllCommands(game.getGameID());
                for(ICommand command : commands)
                {
                    System.out.println(count++ + command.getJSON().getClass().toString());
                    if(command.getJSON().getClass().equals(RollNumberJSON.class))
                        System.out.println("\tRolled!: " + ((RollNumberJSON)command.getJSON()).getNumber());
                    command.execute();
                }
            }
            endTransaction(true);
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }

	}
}
