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
				System.out.println("doc is: " + doc);
				DBObject obj = (DBObject)JSON.parse(doc.toJson());
				System.out.println("obj is: " + obj);
				DBObject token = (DBObject) obj.get("token");
				System.out.println("token is: " + token);
				DBObject json = (DBObject) obj.get("json");
				System.out.println("json is: " + json);
				String klass = (String) obj.get("class");
				System.out.println("klass is: " + klass);
				
				System.out.println("After objects are made");
				
				AuthToken t = new AuthToken();
				
				System.out.println("After token is made");
				
				System.out.println("type is: " + token.get("gameID").getClass());
				
				t.setGameID((Integer)token.get("gameID"));
				System.out.println("After gameID");
				t.setName((String) token.get("name"));
				System.out.println("After name");
				t.setPassword((String) token.get("password"));
				System.out.println("After password");
				t.setPlayerID((Integer) token.get("playerID"));
				System.out.println("After playerID");
				
				System.out.println("After token is made");
				
				Type type = IJavaJSON.getTypeFromString(klass);
				
				System.out.println("After objects are made");
				IJavaJSON j = new Gson().fromJson(json.toString(), type);
				
				System.out.println("After objects are made");
				
				System.out.println("Before factory");
				
				CommandFactory factory = new CommandFactory(facade);
				
				System.out.println("After factory");
				ICommand command = factory.buildCommand(t, j);
				
				System.out.println("after command");
				System.out.println("command.gameID is: " + command.getGameID());
				System.out.println("command.class is: " + command.getClass().toString());
				System.out.println("command.Json.getClass() is: " + command.getJSON().getClass().toString());
				System.out.println("command.Json is: " + command.getJSON());
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
