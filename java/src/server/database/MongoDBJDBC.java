package server.database;

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

public class MongoDBJDBC implements IPersistencePlugin {
	MongoClient mongoClient;
	
	
	@Override
	public void startTransaction() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );		
	}

	@Override
	public void stopTransaction() throws DatabaseException {
		mongoClient.close();		
	}

	@Override
	public IGameDAO getGameDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUserDAO getUserDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommandDAO getCommandDAO() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
