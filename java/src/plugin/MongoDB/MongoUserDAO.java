package plugin.MongoDB;

import server.data.UserData;
import server.database.IUserDAO;
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

import org.bson.Document;

public class MongoUserDAO implements IUserDAO {

	private MongoClient mongoClient;
	
	public MongoUserDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addUser(UserData user) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserData getUser(int userID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserData[] getAllUsers() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(int userID) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

}
