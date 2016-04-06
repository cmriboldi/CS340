package plugin.MongoDB;

import server.database.ICommandDAO;
import server.database.IGameDAO;
import server.database.IPersistencePlugin;
import server.database.IUserDAO;
import server.exception.DatabaseException;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;

import org.bson.Document;

/**
 * Created by Joshua Powers on 4/4/2016.
 */

public class MongoPlugin implements IPersistencePlugin {
	
	private MongoClient mongoClient;
	
	@Override
	public void startTransaction() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );		
	}

	@Override
	public void stopTransaction() throws DatabaseException {
		mongoClient.close();		
	}

	@Override
	public void thaw() throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGameDAO getGameDAO() throws DatabaseException {
		return new MongoGameDAO(mongoClient);
	}

	@Override
	public IUserDAO getUserDAO() throws DatabaseException {
		return new MongoUserDAO(mongoClient);
	}

	@Override
	public ICommandDAO getCommandDAO() throws DatabaseException {
		return new MongoCommandDAO(mongoClient);
	}

	@Override
	public void clear() throws DatabaseException
	{
		// TODO Auto-generated method stub
		
	}
}

