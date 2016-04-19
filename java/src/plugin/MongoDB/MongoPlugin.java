package plugin.MongoDB;

import com.google.inject.Inject;
import plugin.IPluginData;
import server.command.ICommand;
import server.data.UserData;
import server.database.GameData;
import plugin.ICommandDAO;
import plugin.IGameDAO;
import plugin.IPersistencePlugin;
import plugin.IUserDAO;
import server.exception.DatabaseException;
import server.exception.ServerException;
import server.facade.IServerFacade;

import com.mongodb.MongoClient;
import com.mongodb.DB;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Set;

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
	
	@Inject
	public MongoPlugin(IServerFacade facade, IPluginData plugData)
	{
		this.facade = facade;
		this.N = plugData.getCheckinSize();
	}
	
	@Override
	public void startTransaction() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );
		gameDAO = new MongoGameDAO(mongoClient,facade);
		userDAO = new MongoUserDAO(mongoClient);
		commandDAO = new MongoCommandDAO(mongoClient, facade);
	}

	@Override
	public void endTransaction(boolean commit) throws DatabaseException {
		String gameID = commandDAO.getLastGameId();
		if(gameID != null)
		{
			ICommand[] commands = commandDAO.getAllCommands(Integer.parseInt(gameID));
			if(commands.length > 10)
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
		}
		mongoClient.close();		
	}

	@Override
	public void thaw() throws DatabaseException {
		mongoClient = new MongoClient( "localhost" , 27017 );
		gameDAO = new MongoGameDAO(mongoClient,facade);
		userDAO = new MongoUserDAO(mongoClient);
		commandDAO = new MongoCommandDAO(mongoClient, facade);
		
		UserData[] users = userDAO.getAllUsers();
		GameData[] games = gameDAO.getAllGames();
		
		for(UserData user : users)
		{
			try {
				facade.getDatabase().addUser(user);
			} catch (ServerException e) {
				e.printStackTrace();
			}
		}
		for(GameData game : games)
		{
			int gameID = game.getGameID();
			try {
				facade.getDatabase().addGame(game.getName(), game.getModel());
			} catch (ServerException e) {
				e.printStackTrace();
			}
			ICommand[] commands = commandDAO.getAllCommands(gameID);
			for(ICommand command : commands)
			{
				try {
					command.execute();
				} catch (ServerException e) {
					e.printStackTrace();
				}
			}
		}
		mongoClient.close();
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
		mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("Catan");
		db.dropDatabase();
		mongoClient.close();
	}
}

