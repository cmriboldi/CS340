package plugin.MongoDB;

import com.google.inject.Inject;
import plugin.IPluginData;
import server.database.ICommandDAO;
import server.database.IGameDAO;
import plugin.IPersistencePlugin;
import server.database.IUserDAO;
import server.exception.DatabaseException;
import server.facade.IServerFacade;

import com.mongodb.MongoClient;
import com.mongodb.DB;

/**
 * Created by Joshua Powers on 4/4/2016.
 */

public class MongoPlugin implements IPersistencePlugin {
	
	private MongoClient mongoClient;
	private IServerFacade facade;

	@Inject
	public MongoPlugin(IServerFacade facade, IPluginData plugData)
	{
		this.facade = facade;
	}
	
	@Override
	public void startTransaction() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );		
	}

	@Override
	public void endTransaction(boolean commit) throws DatabaseException {
		mongoClient.close();		
	}

	@Override
	public void thaw() throws DatabaseException {
		
	}

	@Override
	public IGameDAO getGameDAO() throws DatabaseException {
		return new MongoGameDAO(mongoClient,facade);
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
		DB db = mongoClient.getDB("Catan");
		db.dropDatabase();
	}
}

