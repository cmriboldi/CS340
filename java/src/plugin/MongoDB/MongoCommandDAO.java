package plugin.MongoDB;

import server.command.ICommand;
import server.database.ICommandDAO;
import server.exception.DatabaseException;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;

public class MongoCommandDAO implements ICommandDAO {

	private MongoClient mongoClient;
	
	public MongoCommandDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addCommand(ICommand command) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICommand getCommand(int commandID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommand[] getAllCommands(int gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommand[] getAllCommands(int gameID, int index) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCommand(int commandID) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

}
