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
		model.name = game.getName();
		String id = Integer.toString(game.getGameID());
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document origin = coll.find().first();
		if(origin == null)
		{
			origin = new Document();
			origin.append(id, model);
			coll.insertOne(origin);
		}
		else
		{
			Document replace = new Document(origin);		
			replace.append(id, model);		
			coll.findOneAndReplace(origin, replace);
		}
	}

	@Override
	public GameData getGame(int gameID) throws DatabaseException {
		String id = Integer.toString(gameID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Games");
		Document doc = coll.find().first();		
		CatanModel model = (CatanModel) doc.get(id);	
		return new GameData(gameID,model.name,model);
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
				CatanModel model = (CatanModel) doc.get(id);		
				games[i] = new GameData(Integer.parseInt(id),model.name,model);////////////////////
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
		
		String name = ((CatanModel) origin.get(Integer.toString(gameID))).name;
		model.name = name;
					
		replace.append(Integer.toString(gameID), model);		
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
