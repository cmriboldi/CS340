package SQLPlugin.src.serverProxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import SQLPlugin.src.client.data.GameInfo;
import SQLPlugin.src.client.data.PlayerInfo;
import communication.*;
import definitions.CatanColor;
import definitions.LogLevel;
import definitions.ResourceType;
import server.exception.ServerException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.CatanModel;
import model.resources.ResourceList;
import com.google.gson.*;


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
	private PlayerInfo localPlayerInfo;
	private AuthProxy authProxy;
	
	public RealProxy()
	{
		urlBase = "http://localhost:8081";
	}

	public RealProxy(String host, int port)
	{
		urlBase = "http://" + host + ":" + port;
	}

	@Override
	public void userLogin(String username, String password) throws ServerException
	{
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
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String cookie = conn.getHeaderField("Set-cookie");
				cookie = cookie.replace("catan.user=", "");
				cookie = cookie.replace(";Path=/;", "");
				authProxy = new AuthProxy(urlBase, cookie);
				String decodedCookie = URLDecoder.decode(cookie, "UTF-8");
				System.out.println("Decoded cookie:" + decodedCookie);
				JsonObject userjson = new Gson().fromJson(decodedCookie, JsonObject.class);
				PlayerInfo localPlayerInfo = new PlayerInfo();
				System.out.println("userjson: " + userjson.toString());
				String name = userjson.get("name").toString().replace("\"", "");
				localPlayerInfo.setName(name);
				localPlayerInfo.setId(userjson.get("playerID").getAsInt());
				this.localPlayerInfo = localPlayerInfo;
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
			throw new ServerException("SERVER NOT RESPONDING");
		}
	}

	@Override
	public GameInfo[] listGames() throws ServerException
	{
		String response = (String) get("/games/list");
		JsonArray json = new Gson().fromJson(response, JsonArray.class);
		GameInfo[] games = new GameInfo[json.size()];
		for(int i = 0; i < json.size(); i++)
		{
			JsonObject game = (JsonObject) json.get(i);
			String title = game.get("title").toString().replace("\"", "");
			int id = game.get("id").getAsInt();
			JsonArray players = game.getAsJsonArray("players");
			
			List<PlayerInfo> playerList = new ArrayList<PlayerInfo>();
			for(int j = 0; j < players.size(); j++)
			{
				PlayerInfo player = new Gson().fromJson(players.get(j), PlayerInfo.class);
				if(player.getName() != "")
				{
					playerList.add(player);
				}
			}
			GameInfo newGame = new GameInfo(id,title,playerList);
			games[i] = newGame;
		}
		return games;
	}

	@Override
	public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String title) throws ServerException
	{
		CreateGameJSON json = new CreateGameJSON(randomTiles, randomNumbers, randomPorts, title);
		String response = (String) post("/games/create", json);
		JsonObject game = new Gson().fromJson(response, JsonObject.class);
		List<PlayerInfo> players = new ArrayList<>();
		JsonArray playersArray = game.getAsJsonArray("players");
		for(int i = 0; i < playersArray.size(); i++)
		{
			PlayerInfo player = new Gson().fromJson(playersArray.get(i), PlayerInfo.class);
			players.add(player);
		}
		GameInfo newGame = new GameInfo(Integer.parseInt(game.get("id").toString()), game.get("title").toString(), players);
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
	public CatanModel getGameModel() throws ServerException
	{
		return authProxy.getGameModel();
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
	public CatanModel setCommands(JsonArray commands) throws ServerException 
	{
		return authProxy.setCommands(commands);
	}

	@Override
	public JsonArray getCommands() throws ServerException 
	{
		return authProxy.getCommands();
	}

	@Override
	public void addAI(String AIType) throws ServerException 
	{
		authProxy.addAI(AIType);
	}

	@Override
	public String[] listAI() throws ServerException
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
	public CatanModel acceptTrade(int playerIndex, boolean willAccept) throws ServerException 
	{
		return authProxy.acceptTrade(playerIndex, willAccept);
	}

	@Override
	public CatanModel maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) throws ServerException 
	{
		return authProxy.maritimeTrade(playerIndex, ratio, input, output);
	}

	@Override
	public CatanModel discardCards(int playerIndex, ResourceList discardedCards) throws ServerException 
	{
		return authProxy.discardCards(playerIndex, discardedCards);
	}

	@Override
	public void changeLogLevel(LogLevel logLevel) throws ServerException
	{
		ChangeLogLevelJSON data = new ChangeLogLevelJSON(logLevel.toString());
		post("/util/changeLogLevel", data);
	}
	
	@Override
	public PlayerInfo getLocalPlayerInfo()
	{
		return localPlayerInfo;
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
			throw new ServerException("SERVER NOT RESPONDING");
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
			conn.connect();
			
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] send = new Gson().toJson(data).getBytes();
			writer.write(send);
			writer.close();
			
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
			throw new ServerException("SERVER NOT RESPONDING");
		}
		return null;
	}
}
