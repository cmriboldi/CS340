package plugin.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.data.UserData;
import server.database.IUserDAO;
import server.exception.DatabaseException;

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

    @Override
    public void addUser(UserData user) throws DatabaseException {
    	PreparedStatement stmt = null; 
    	
    	try {
    		String query = "insert into users (username, password) values (? , ? )"; 
    		try {
				stmt = database.getConnection().prepareStatement(query);
				stmt.setString(1, user.getName());
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
    	}
    	
    	
    }

    @Override
    public UserData getUser(int userID) throws DatabaseException {
        
    	PreparedStatement stmt = null; 
    	ResultSet rs = null; 
    	String username = null; 
    	String password = null; 
    	
		String query = "select * from users where userID = ?"; 
		try {
			stmt = database.getConnection().prepareStatement(query);
			stmt.setInt(1, userID);
			
			rs = stmt.executeQuery(); 
			
			if (rs.next())
			{
		    	username  = rs.getString(1); 
		    	password = rs.getString(2); 
			}
	    	
			stmt.close();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		 
    	return new UserData(username, password);
    }

    @Override
    public UserData[] getAllUsers() throws DatabaseException {
    	
    	PreparedStatement stmt = null; 
    	ResultSet rs = null; 

    	
    	UserData[] returnUsers = null; 
    	
		String query = "select * from users"; 
		try {
			stmt = database.getConnection().prepareStatement(query);
			
			rs = stmt.executeQuery(); 
			
			int rowcount = 0;
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			returnUsers = new UserData[rowcount]; 
			int index = 0; 
			
			while (rs.next()) {
			  // do your standard per row stuff
				returnUsers[index] = new UserData(rs.getString(1), rs.getString(2)); 
				index ++; 
			}
			

	    	
			stmt.close();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
    	
        return returnUsers;
    }

    @Override
    public void deleteUser(int userID) throws DatabaseException {

    }
}
