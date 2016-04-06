package plugin.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import server.command.ICommand;
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
    public void addCommand(ICommand command) throws DatabaseException {

    	PreparedStatement stmt = null; 
    	
    	/*
    	try {
    		String query = "insert into command (game_id, order_of_execution, command) values (? , ?, ? )"; 
    		try {
				stmt = database.getConnection().prepareStatement(query);
				stmt.setString(1,command.toString());
	    		stmt.setString(2, user.getPassword()); 
	    		if (stmt.executeUpdate() != 1)
	    		{
	    			throw new DatabaseException(" add user failed"); 
	    		}
	    		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
    	
    		
    	}
    	finally{
    		try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} */
    	
    }

    @Override
    public ICommand getCommand(int commandID) throws DatabaseException {
        return null;
    }

    @Override
    public ICommand[] getAllCommands(int gameID) throws DatabaseException {
        return new ICommand[0];
    }

    @Override
    public ICommand[] getAllCommands(int gameID, int index) throws DatabaseException {
        return new ICommand[0];
    }

    @Override
    public void deleteCommand(int commandID) throws DatabaseException {

    }
}
