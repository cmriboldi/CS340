package plugin.MongoDB;

import model.CatanModel;
import server.database.GameData;
import server.database.IGameDAO;
import server.exception.DatabaseException;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.JSONSerializer;
import serverProxy.JSONDeserializer;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;

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

public class MongoGameDAO implements IGameDAO{
	
	private MongoClient mongoClient;
	private IServerFacade facade;
	
	public MongoGameDAO(MongoClient mongoClient, IServerFacade facade) {
		this.mongoClient = mongoClient;
		this.facade = facade;
	}
	
	@Override
	public void addGame(GameData game) throws DatabaseException {
		CatanModel model = game.getModel();
		String id = Integer.toString(game.getGameID());
		String json = JSONSerializer.serialize(model);
		DBObject dbobject = (DBObject)JSON.parse(json);
		dbobject.put("Name", game.getName());
		
		System.out.println(id);
		System.out.println(dbobject);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document origin = coll.find().first();
		if(origin == null)
		{
			origin = new Document();
			origin.append(id, dbobject);
			coll.insertOne(origin);
		}
		else
		{
			Document replace = new Document(origin);		
			replace.append(id, dbobject);		
			coll.findOneAndReplace(origin, replace);
		}
	}

	@Override
	public GameData getGame(int gameID) throws DatabaseException {
		String id = Integer.toString(gameID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document doc = coll.find().first();
		DBObject dbobject = (DBObject) doc.get(id);
		String name = (String) dbobject.get("Name");
		dbobject.removeField("Name");
		String json = JSON.serialize(dbobject);
		
		CatanModel model = null;
		try {
			model = JSONDeserializer.deserialize(json);
		} catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) {
			e.printStackTrace();
		}		
		return new GameData(gameID,name,model);
	}

	@Override
	public GameData[] getAllGames() throws DatabaseException {
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document doc = coll.find().first();
		if(doc != null)
		{
			Set<String> ids = doc.keySet();
			GameData[] games = new GameData[ids.size()];
			
			int i = 0;
			for(String id : ids)
			{
				DBObject dbobject = (DBObject) doc.get(id);
				String name = (String) dbobject.get("Name");
				dbobject.removeField("Name");
				String json = JSON.serialize(dbobject);
				
				CatanModel model = null;
				try {
					model = JSONDeserializer.deserialize(json);
				} catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) {
					e.printStackTrace();
				}		
				games[i] = new GameData(Integer.parseInt(id),name,model);////////////////////
				i++;
			}
			return games;
		}
		else
		{
			return new GameData[0];
		}
	}

	@Override
	public void updateGame(int gameID) throws DatabaseException {
		CatanModel model = null;
		try {
			model = facade.getDatabase().getGameModel(gameID);
		} catch (ServerException e) {
			e.printStackTrace();
		}
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document origin = coll.find().first();
		Document replace = new Document(origin);
		
		System.out.println(gameID);
		System.out.println(origin.get(Integer.toString(gameID)));
		
		DBObject obj = (DBObject) origin.get(Integer.toString(gameID));
		String name = (String) obj.get("Name");
		String json = JSONSerializer.serialize(model);
		DBObject dbobject = (DBObject)JSON.parse(json);
		dbobject.put("Name", name);		
					
		replace.append(Integer.toString(gameID), dbobject);		
		coll.findOneAndReplace(origin, replace);	
	}

	@Override
	public void deleteGame(int gameID) throws DatabaseException {
		String id = Integer.toString(gameID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document origin = coll.find().first();
		Document replace = new Document(origin);
		
		replace.remove(id);
		coll.findOneAndReplace(origin, replace);		
	}

}
