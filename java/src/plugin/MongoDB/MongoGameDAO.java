package plugin.MongoDB;

import model.CatanModel;
import server.database.GameData;
import server.database.IGameDAO;
import server.exception.DatabaseException;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;

public class MongoGameDAO implements IGameDAO{
	
	private MongoClient mongoClient;
	
	public MongoGameDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addGame(CatanModel game) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameData getGame(int gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameData[] getAllGames() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGame(int gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGame(int gameID) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

}
