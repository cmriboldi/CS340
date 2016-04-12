
import exception.DatabaseException;
import plugin.IUserDAO;
import server.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLUserDAO implements IUserDAO
{
    private final SQLPlugin database;

    public SQLUserDAO(SQLPlugin database)
    {
        this.database = database;
    }

    /*
        CREATE TABLE users
		(
		id INT PRIMARY KEY NOT NULL,
		user_name TEXT NOT NULL,
		user_password TEXT NOT NULL,
		);
     */
    
    
    @Override
    public void addUser(UserData user) throws DatabaseException {
    	PreparedStatement stmt = null; 
    	
    	try {
    		String query = "insert into users (user_name, user_password) values (? , ? )"; 
    		try {
				stmt = database.getConnection().prepareStatement(query);
				stmt.setString(1, user.getName());
	    		stmt.setString(2, user.getPassword()); 
	    		if (stmt.executeUpdate() != 1)
	    		{
	    			throw new DatabaseException(" add user failed"); 
	    		}
				stmt.close();
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
    	}
    	
    	
    }

    @Override
    public UserData getUser(int userID) throws DatabaseException {
        
    	PreparedStatement stmt = null; 
    	ResultSet rs = null; 
    	String username = null; 
    	String password = null; 
    	
		String query = "select * from users where id = ?"; 
		try
		{
			stmt = database.getConnection().prepareStatement(query);
			stmt.setInt(1, userID + 1);
			
			rs = stmt.executeQuery(); 
			
			if (rs.next())
			{
		    	username = rs.getString(2);
		    	password = rs.getString(3);
			}

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
		 
    	return new UserData(username, password);
    }

    @Override
    public UserData[] getAllUsers() throws DatabaseException
	{
		Statement stmt = null;
    	ResultSet rs = null;
    	UserData[] returnUsers = null; 
    	
		String query = "select * from users"; 
		try
		{
			stmt = database.getConnection().createStatement();
			rs = stmt.executeQuery(query);

			List<UserData> users = new ArrayList<>();
			while(rs.next())
			{
				UserData newUser = new UserData();
				newUser.setId(rs.getInt(1) - 1);
				newUser.setName(rs.getString(2));
				newUser.setPassword(rs.getString(3));
				users.add(newUser);
			}

			returnUsers = new UserData[users.size()];
			users.toArray(returnUsers);
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
    	
        return returnUsers;
    }

    @Override
    public void deleteUser(int userID) throws DatabaseException {
    	
    	PreparedStatement stmt = null; 
    	
    	try {
    		String query = "delete from users where id = ?"; 
    		try {
				stmt = database.getConnection().prepareStatement(query);
				stmt.setInt(1, userID);
	    		if (stmt.executeUpdate() != 1)
	    		{
	    			throw new DatabaseException(" delete user failed"); 
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
    	}
    }
}
