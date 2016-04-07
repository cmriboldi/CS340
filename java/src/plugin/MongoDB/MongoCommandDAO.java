package plugin.MongoDB;

import server.command.ICommand;
import server.data.UserData;
import server.database.ICommandDAO;
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
import java.util.Set;

import org.bson.Document;

public class MongoCommandDAO implements ICommandDAO {

	private MongoClient mongoClient;
	
	public MongoCommandDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addCommand(ICommand command) throws DatabaseException {
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Commands");
		Document origin = coll.find().first();
		Document replace = new Document(origin);
		
		replace.append(command.toString(), command);	
		coll.findOneAndReplace(origin, replace);
		
	}

	@Override
	public ICommand getCommand(int commandID) throws DatabaseException {
		String id = Integer.toString(commandID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Commands");
		Document doc = coll.find().first();
		ICommand command = (ICommand)doc.get(id);
		
		return command;
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
	public ICommand[] getAllCommands(int gameID, int index) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCommand(int commandID) throws DatabaseException {
		String id = Integer.toString(commandID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Commands");
		Document origin = coll.find().first();
		Document replace = new Document(origin);
		
		replace.remove(id);
		coll.findOneAndReplace(origin, replace);		
	}

}
