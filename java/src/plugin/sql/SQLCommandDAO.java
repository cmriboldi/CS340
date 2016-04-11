package plugin.sql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.*;

import server.command.ICommand;
import server.data.UserData;
import server.database.ICommandDAO;
import server.exception.DatabaseException;

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
		byte[] data = new Gson().toJson(command).getBytes();
    	
    	try
		{
    		String query = "insert into command (game_id, command) values (? , ? )"; 
    		try
			{
				stmt = database.getConnection().prepareStatement(query);
				stmt.setInt(1,gameID);
				stmt.setBytes(2, data);
	    		if (stmt.executeUpdate() != 1)
	    		{
	    			throw new DatabaseException(" add user failed"); 
	    		}
	    		
			}
			catch (SQLException e)
			{
				throw new DatabaseException(e.getMessage());
			}     		
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
    	
    }

    @Override
    public ICommand getCommand(int commandID) throws DatabaseException {
        return null;
    }

    @Override
    public ICommand[] getAllCommands(int gameID) throws DatabaseException {
    	
    	PreparedStatement stmt = null; 

       	ResultSet rs = null; 
    	
    	ICommand[] returnCommands = null; 
    	
		String query = "select * from command where game_id = ?"; 
		try {
			stmt = database.getConnection().prepareStatement(query);
			stmt.setInt(1,gameID);
						
			rs = stmt.executeQuery(); 
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			returnCommands = new ICommand[rowcount]; 
			int index = 0; 
			
			while (rs.next()) {
			  // do your standard per row stuff
				ICommand newCommand = new Gson().fromJson(rs.toString(), ICommand.class); 
				returnCommands[index] = newCommand;  // MAKE NEW COMMAND OBJECTS HERE,
				index ++; 
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}

		}

        return returnCommands;
    }

    @Override
    public void deleteCommand(int commandID) throws DatabaseException {

    }
}
