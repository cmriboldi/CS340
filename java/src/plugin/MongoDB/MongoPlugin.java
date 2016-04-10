package plugin.MongoDB;

import com.google.inject.Inject;
import plugin.IPluginData;
import server.command.ICommand;
import server.database.ICommandDAO;
import server.database.IGameDAO;
import plugin.IPersistencePlugin;
import server.database.IUserDAO;
import server.exception.DatabaseException;
import server.facade.IServerFacade;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bson.Document;

/**
 * Created by Joshua Powers on 4/4/2016.
 */

public class MongoPlugin implements IPersistencePlugin {
	
	private MongoClient mongoClient;
	private IServerFacade facade;
	private MongoGameDAO gameDAO;
	private MongoUserDAO userDAO;
	private MongoCommandDAO commandDAO;
	
	private int N;
	
	public MongoPlugin(IServerFacade facade, int N)
	{
		this.facade = facade;
		this.N = N;
	}
	
	public MongoPlugin(IServerFacade facade)
	{
		this.facade = facade;
		this.N = 10;
	}
	
	@Override
	public void startTransaction() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );
		gameDAO = new MongoGameDAO(mongoClient,facade);
		userDAO = new MongoUserDAO(mongoClient);
		commandDAO = new MongoCommandDAO(mongoClient);
	}

	@Override
	public void endTransaction(boolean commit) throws DatabaseException {
		String gameID = commandDAO.getLastGameId();
		ICommand[] commands = commandDAO.getAllCommands(Integer.parseInt(gameID));
		if(commands.length >= 10)
		{
			gameDAO.updateGame(Integer.parseInt(gameID));
			MongoDatabase db = mongoClient.getDatabase("Catan");
			MongoCollection<Document> coll = db.getCollection("Commands");
			Document origin = coll.find().first();
			Document replace = new Document(origin);
			Set<String> ids = origin.keySet();
			
			for(String id : ids)
			{
				if(id.equals(gameID))
				{
					replace.remove(gameID);
				}
			}
			
			coll.findOneAndReplace(origin, replace);
		}
		mongoClient.close();		
	}

	@Override
	public void thaw() throws DatabaseException {
		
	}

	@Override
	public IGameDAO getGameDAO() throws DatabaseException {
		return gameDAO;
	}

	@Override
	public IUserDAO getUserDAO() throws DatabaseException {
		return userDAO;
	}

	@Override
	public ICommandDAO getCommandDAO() throws DatabaseException {
		return commandDAO;
	}

	@Override
	public void clear() throws DatabaseException
	{
		DB db = mongoClient.getDB("Catan");
		db.dropDatabase();
	}
}

