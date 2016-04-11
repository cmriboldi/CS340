package plugin.sql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.data.UserData;
import server.database.ICommandDAO;
import server.exception.DatabaseException;
import shared.communication.JSON.IJavaJSON;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLCommandDAO implements ICommandDAO
{
    private final SQLPlugin database;
    
    
    /*  CREATE TABLE command

     	command_id INT PRIMARY KEY NOT NULL,
		game_id INT NOT NULL,
		order_of_execution INT NOT NULL,
		command BLOB NOT NULL,
     */

    public SQLCommandDAO(SQLPlugin database)
    {
        this.database = database;
    }


    @Override
    public void addCommand(ICommand command) throws DatabaseException
	{
    	PreparedStatement stmt = null;
    	int gameID = command.getGameID();

		String query = "insert into command (game_id, command_type, auth_token, json) values (? , ?, ?, ?)";
		try
		{
			stmt = database.getConnection().prepareStatement(query);
			stmt.setInt(1,gameID);
			stmt.setString(2, command.getClass().toString());
			stmt.setBytes(3, new Gson().toJson(command.getAuthToken()).getBytes());
			stmt.setBytes(4, new Gson().toJson(command.getJSON()).getBytes());
			if (stmt.executeUpdate() != 1)
			{
				throw new DatabaseException(" add user failed");
			}
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new DatabaseException(e.getMessage());
		}
    }

    @Override
    public ICommand getCommand(int commandID) throws DatabaseException {
        return null;
    }

    @Override
    public ICommand[] getAllCommands(int gameID) throws DatabaseException
	{
    	PreparedStatement stmt = null;
       	ResultSet rs = null;
    	ICommand[] returnCommands = null; 
    	
		String query = "select * from command where game_id = ?"; 
		try
		{
			stmt = database.getConnection().prepareStatement(query);
			stmt.setInt(1,gameID);
						
			rs = stmt.executeQuery(); 

			List<ICommand> commands = new ArrayList<>();
			CommandFactory factory = new CommandFactory(database.getFacade());
			while(rs.next())
			{
				String command_type = rs.getString(3);
				Type type = IJavaJSON.getTypeFromString(command_type);
				AuthToken token = new Gson().fromJson(new String(rs.getBytes(4)), AuthToken.class);
				IJavaJSON json = new Gson().fromJson(new String(rs.getBytes(5)), type);
				ICommand command = factory.buildCommand(token, json);
				commands.add(command);
			}
			
			returnCommands = new ICommand[commands.size()];
			commands.toArray(returnCommands);

			stmt.close();
		}
		catch (SQLException e)
		{
			throw new DatabaseException(e.getMessage());
		}
		finally
		{
			try
			{
				stmt.close();
			}
			catch (SQLException e)
			{
				throw new DatabaseException(e.getMessage());
			}

		}

        return returnCommands;
    }

    @Override
    public void deleteCommand(int commandID) throws DatabaseException {

    }
}
