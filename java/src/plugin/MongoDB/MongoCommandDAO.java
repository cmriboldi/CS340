package plugin.MongoDB;

import server.AuthToken;
import server.command.ICommand;
import server.data.UserData;
import server.database.ICommandDAO;
import server.exception.DatabaseException;
import shared.communication.JSON.IJavaJSON;

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
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.Set;

import org.bson.Document;

public class MongoCommandDAO implements ICommandDAO {

	private MongoClient mongoClient;
	private String gameID;
	
	public MongoCommandDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;		
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
			obj.put("token", gson.toJson(token));
			obj.put("json", gson.toJson(json));
			obj.put("class", gson.toJson(klass));
			origin.append(gameID, obj);
			coll.insertOne(origin);
		}
		else
		{
			Document replace = new Document(origin);
			DBObject obj = new BasicDBObject();
			obj.put("command", command);
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
		Document doc = coll.find().first();
		Set<String> ids = doc.keySet();
		
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
				ICommand command = (ICommand) doc.get(id);
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
