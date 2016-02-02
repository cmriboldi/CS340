package serverProxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import model.CatanModel;
import model.resources.ResourceList;
import shared.communication.*;
import shared.communication.JSON.*;
import shared.definitions.CatanColor;
import shared.definitions.Command;
import shared.definitions.LogLevel;
import shared.definitions.ResourceType;
import shared.locations.*;

/**
 * The 'real' proxy will communicate with the server over http protocol to interact with the server
 * api.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class RealProxy implements ServerProxy
{
	private String urlBase;
	private String name;
	private int playerID;
	private AuthProxy authProxy;
	
	public RealProxy()
	{
		urlBase = "http://localhost:8081";
	}

	@Override
	public void userLogin(String username, String password) throws ServerException 
	{
		System.out.println("User Login");
		login("/user/login", username, password);
	}

	@Override
	public void userRegister(String username, String password) throws ServerException 
	{
		login("/user/register", username, password);
	}
	
	private void login(String urlPath, String username, String password) throws ServerException
	{
		CommUser user = new CommUser(username, password);
		System.out.println("Login User");
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] data = new Gson().toJson(user).getBytes();
			writer.write(data);
			writer.close();
			
			System.out.println(conn.getResponseCode());
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String cookie = conn.getHeaderField("Set-cookie");
				cookie = cookie.replace("catan.user=", "");
				cookie = cookie.replace(";Path=/;", "");
				authProxy = new AuthProxy(cookie);
				String decodedCookie = URLDecoder.decode(cookie, "UTF-8");
				JsonObject userjson = new Gson().fromJson(decodedCookie, JsonObject.class);
				this.name = userjson.get("name").toString().replace("\"", "");
				this.playerID = userjson.get("playerID").getAsInt();
				System.out.println("Set-cookie: " + cookie);
				System.out.println("Decoded: " + URLDecoder.decode(cookie, "UTF-8"));
				System.out.println(name + " " + playerID);
			}
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<CommGame> listGames() throws ServerException 
	{
		System.out.println("List Games");
		String response = (String) get("/games/list");
		JsonArray json = new Gson().fromJson(response, JsonArray.class);
		List<CommGame> games = new ArrayList<CommGame>();
		for(int i = 0; i < json.size(); i++)
		{
			JsonObject game = (JsonObject) json.get(i);
			String title = game.get("title").toString();
			int id = game.get("id").getAsInt();
			JsonArray players = game.getAsJsonArray("players");
			System.out.println(players.toString());
			
			CommPlayer[] playerArray = new CommPlayer[4];
			for(int j = 0; j < players.size(); j++)
			{
				System.out.println("Player: " + players.get(j));
				System.out.println("Class: " + players.get(j).getClass());
				CommPlayer player = new Gson().fromJson(players.get(j), CommPlayer.class);
				playerArray[j] = player;
			}
			CommGame newGame = new CommGame(title,id,playerArray);
			games.add(newGame);
		}
		System.out.println(games.toString());
		return games;
	}

	@Override
	public CommGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException 
	{
		CreateGameJSON json = new CreateGameJSON(randomTiles, randomNumbers, randomPorts, name);
		String response = (String) post("/games/create", json);
		JsonObject game = new Gson().fromJson(response, JsonObject.class);
		CommPlayer[] players = new CommPlayer[4];
		JsonArray playersArray = game.getAsJsonArray("players");
		for(int i = 0; i < playersArray.size(); i++)
		{
			CommPlayer player = new Gson().fromJson(playersArray.get(i), CommPlayer.class);
			players[i] = player;
		}
		CommGame newGame = new CommGame(game.get("title").toString(), Integer.parseInt(game.get("id").toString()), players);
		return newGame;
	}

	@Override
	public void joinGame(int gameId, CatanColor color) throws ServerException 
	{
		authProxy.joinGame(gameId, color.toString());
	}

	@Override
	public void saveGame(int gameId, String fileName) throws ServerException 
	{
		SaveJSON json = new SaveJSON(gameId, fileName);
		post("/games/save", json);
	}

	@Override
	public void loadGame(String fileName) throws ServerException 
	{
		LoadJSON json = new LoadJSON(fileName);
		post("/games/load", json);
	}

	@Override
	public CatanModel getGameModel(int modelNumber) throws ServerException 
	{
		return authProxy.getGameModel(modelNumber);
	}

	@Override
	public CatanModel resetGame() throws ServerException 
	{
		return authProxy.resetGame();
	}

	@Override
	public CatanModel setCommands(List<Command> commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Command> getCommands() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String AIType) throws ServerException 
	{
		authProxy.addAI(AIType);
	}

	@Override
	public List<String> listAI() throws ServerException 
	{
		return authProxy.listAI();
	}

	@Override
	public CatanModel sendChat(int playerIndex, String content) throws ServerException 
	{
		return authProxy.sendChat(playerIndex, content);
	}

	@Override
	public CatanModel rollNumber(int playerIndex, int number) throws ServerException 
	{
		return authProxy.rollNumber(playerIndex, number);
	}

	@Override
	public CatanModel robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) throws ServerException 
	{
		return authProxy.robPlayer(playerIndex, victimIndex, hexLocation);
	}

	@Override
	public CatanModel finishTurn(int playerIndex) throws ServerException 
	{
		return authProxy.finishTurn(playerIndex);
	}

	@Override
	public CatanModel buyDevCard(int playerIndex) throws ServerException 
	{
		return authProxy.buyDevCard(playerIndex);
	}

	@Override
	public CatanModel yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) throws ServerException 
	{
		return authProxy.yearOfPlenty(playerIndex, resource1, resource2);
	}

	@Override
	public CatanModel roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws ServerException 
	{
		return authProxy.roadBuilding(playerIndex, spot1, spot2);
	}

	@Override
	public CatanModel soldier(int playerIndex, int victimIndex, HexLocation hexLocation) throws ServerException 
	{
		return authProxy.soldier(playerIndex, victimIndex, hexLocation);
	}

	@Override
	public CatanModel monopoly(int playerIndex, ResourceType resource) throws ServerException 
	{
		return authProxy.monopoly(playerIndex, resource);
	}

	@Override
	public CatanModel monument(int playerIndex) throws ServerException 
	{
		return authProxy.monument(playerIndex);
	}

	@Override
	public CatanModel buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) throws ServerException 
	{
		return authProxy.buildRoad(playerIndex, roadLocation, free);
	}

	@Override
	public CatanModel buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) throws ServerException 
	{
		return authProxy.buildSettlement(playerIndex, vertexLocation, free);
	}

	@Override
	public CatanModel buildCity(int playerIndex, VertexLocation vertexLocation) throws ServerException 
	{
		return authProxy.buildCity(playerIndex, vertexLocation);
	}

	@Override
	public CatanModel offerTrade(int playerIndex, int receiver, ResourceList offer) throws ServerException 
	{
		return authProxy.offerTrade(playerIndex, receiver, offer);
	}

	@Override
	public CatanModel acceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel discardCards(int playerIndex, ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeLogLevel(LogLevel logLevel) throws ServerException {
		// TODO Auto-generated method stub
		
	}
	
	private Object get(String urlPath) throws ServerException
	{
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
				
				return sb.toString();
			}
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private Object post(String urlPath, Object data) throws ServerException
	{
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
//			System.out.println(conn.getRequestProperties().toString());
			conn.connect();
			
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] send = new Gson().toJson(data).getBytes();
			writer.write(send);
			writer.close();
			
//			System.out.println(conn.getResponseCode());
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
				return sb.toString();
			}
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}


}
