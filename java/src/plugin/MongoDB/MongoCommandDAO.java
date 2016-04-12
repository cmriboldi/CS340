package plugin.MongoDB;

import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.data.UserData;
import server.database.ICommandDAO;
import server.exception.DatabaseException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import java.lang.reflect.Type;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.Set;

import org.bson.Document;

public class MongoCommandDAO implements ICommandDAO {

	private MongoClient mongoClient;
	private IServerFacade facade; 
	private String gameID;
	
	public MongoCommandDAO(MongoClient mongoClient, IServerFacade facade) {
		this.mongoClient = mongoClient;	
		this.facade = facade;
	}
	
	public String getLastGameId()
	{
		return gameID;
	}
	
	@Override
	public void addCommand(ICommand command) throws DatabaseException {
		gameID = Integer.toString(command.getGameID());
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Commands");
		Document origin = coll.find().first();
		if(origin == null)
		{
			origin = new Document();
			Gson gson = new Gson();
			
			DBObject obj = new BasicDBObject();		
			AuthToken token = command.getAuthToken();
			IJavaJSON json = command.getJSON();
			String klass = command.getClass().toString();
			obj.put("token", JSON.parse(gson.toJson(token)));
			obj.put("json", JSON.parse(gson.toJson(json)));
			obj.put("class", JSON.parse(gson.toJson(klass)));
			
			System.out.println("obj is: " + obj);
			
			origin.append(gameID, obj);
			coll.insertOne(origin);
		}
		else
		{
			Document replace = new Document(origin);
			Gson gson = new Gson();
			
			DBObject obj = new BasicDBObject();		
			AuthToken token = command.getAuthToken();
			IJavaJSON json = command.getJSON();
			String klass = command.getClass().toString();

			obj.put("token", JSON.parse(gson.toJson(token)));
			obj.put("json", JSON.parse(gson.toJson(json)));
			obj.put("class", JSON.parse(gson.toJson(klass)));
			
			System.out.println("obj is: " + obj);
			
			replace.append(gameID, obj);
			coll.findOneAndReplace(origin, replace);
		}		
	}

	@Override
	public ICommand getCommand(int commandID) throws DatabaseException {
		return null;
	}

	@Override
	public ICommand[] getAllCommands(int gameID) throws DatabaseException {
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Commands");
		Document origin = coll.find().first();
		Set<String> ids = origin.keySet();
		
		int n = 0;
		for(String id : ids)
		{
			if(id.equals(Integer.toString(gameID)))
					n++;
		}
		ICommand[] commands = new ICommand[n];
		
		int i = 0;
		for(String id : ids)
		{
			if(id.equals(Integer.toString(gameID)))
			{
				Document doc = (Document) origin.get(id);
				DBObject obj = (DBObject)JSON.parse(doc.toJson());
				DBObject token = (DBObject) obj.get("token");
				DBObject json = (DBObject) obj.get("json");
				String klass = (String) obj.get("class");
				
				AuthToken t = new AuthToken();
				t.setGameID(Integer.parseInt((String) token.get("gameID")));
				t.setName((String) token.get("name"));
				t.setPassword((String) token.get("password"));
				t.setPlayerID(Integer.parseInt((String) token.get("playerID")));
				
				Type type = IJavaJSON.getTypeFromString(klass);
				IJavaJSON j = new Gson().fromJson(json.toString(), type);
				
				CommandFactory factory = new CommandFactory(facade);
				ICommand command = factory.buildCommand(t, j);			
				commands[i] = command;
				i++;
			}
		}
		
		return commands;
	}


	@Override
	public void deleteCommand(int commandID) throws DatabaseException {
		throw new DatabaseException("This method should not be called");
	}

}
