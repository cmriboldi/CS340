package plugin.MongoDB;

import model.CatanModel;
import server.database.GameData;
import server.database.IGameDAO;
import server.exception.DatabaseException;
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
	
	public MongoGameDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addGame(GameData game) throws DatabaseException {
		CatanModel model = game.getModel();
		String id = Integer.toString(game.getGameID());///////////////
		String json = JSONSerializer.serialize(model);
		DBObject dbobject = (DBObject)JSON.parse(json);
		
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
		String json = JSON.serialize(doc.get(id));
		
		CatanModel model = null;
		try {
			model = JSONDeserializer.deserialize(json);
		} catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) {
			e.printStackTrace();
		}		
		return new GameData(gameID,"name",model);////////////////////
	}

	@Override
	public GameData[] getAllGames() throws DatabaseException {
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document doc = coll.find().first();
		Set<String> ids = doc.keySet();
		GameData[] games = new GameData[ids.size()];
		
		int i = 0;
		for(String id : ids)
		{
			String json = JSON.serialize(doc.get(id));
			
			CatanModel model = null;
			try {
				model = JSONDeserializer.deserialize(json);
			} catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) {
				e.printStackTrace();
			}		
			games[i] = new GameData(Integer.parseInt(id),"name",model);////////////////////
			i++;
		}
		return games;
	}

	@Override
	public void updateGame(int gameID) throws DatabaseException {
		// TODO Auto-generated method stub		
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
