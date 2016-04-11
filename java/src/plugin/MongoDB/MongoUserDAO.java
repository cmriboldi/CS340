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
import java.util.Set;

import org.bson.Document;

public class MongoUserDAO implements IUserDAO {

	private MongoClient mongoClient;
	
	public MongoUserDAO(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}
	
	@Override
	public void addUser(UserData user) throws DatabaseException {
		String id = Integer.toString(user.getId());
		String name = user.getName();
		String password = user.getPassword();
		
		DBObject object = new BasicDBObject();
		object.put("name", name);
		object.put("password", password);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Users");
		Document origin = coll.find().first();
		if(origin == null)
		{
			origin = new Document();
			origin.append(id, object);
			coll.insertOne(origin);
		}
		else
		{
			Document replace = new Document(origin);
			replace.append(id, object);		
			coll.findOneAndReplace(origin, replace);
		}
		
	}

	@Override
	public UserData getUser(int userID) throws DatabaseException {
		String id = Integer.toString(userID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Users");
		Document doc = coll.find().first();
		DBObject object = (DBObject) doc.get(id);
		String name = (String) object.get("name");
		String password = (String) object.get("password");
		
		UserData user = new UserData(name,password);
		user.setId(userID);
		return user;
	}

	@Override
	public UserData[] getAllUsers() throws DatabaseException {
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Users");
		Document doc = coll.find().first();
		if(doc != null)
		{
			Set<String> ids = doc.keySet();
			UserData[] users = new UserData[ids.size()];
			
			int i = 0;
			for(String id : ids)
			{
				DBObject object = (DBObject) doc.get(id);
				String name = (String) object.get("name");
				String password = (String) object.get("password");
				UserData user = new UserData(name,password);
				user.setId(Integer.parseInt(id));
				users[i] = user;
				i++;
			}
			
			return users;
		}
		else
		{
			return new UserData[0];
		}
	}

	@Override
	public void deleteUser(int userID) throws DatabaseException {
		String id = Integer.toString(userID);
		
		MongoDatabase db = mongoClient.getDatabase("Catan");
		MongoCollection<Document> coll = db.getCollection("Users");
		Document origin = coll.find().first();
		Document replace = new Document(origin);
		
		replace.remove(id);	
		coll.findOneAndReplace(origin, replace);
	}

}
