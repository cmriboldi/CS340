package app.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import app.communication.*;
import app.definitions.ResourceType;
import app.exception.GeneralPlayerException;
import app.exception.InvalidTurnStatusException;
import app.exception.ServerException;
import app.exception.TurnIndexException;
import app.locations.EdgeLocation;
import app.locations.HexLocation;
import app.locations.VertexLocation;
import app.model.CatanModel;
import app.model.resources.ResourceList;
import app.serial.JSONDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class AuthProxy 
{
	private String userCookie;
	private String gameCookie;
	private String urlBase;
	
	public AuthProxy(String urlBase, String authCookie)
	{
		this.userCookie = "catan.user=" + authCookie;
		this.gameCookie = "";
		this.urlBase = urlBase;
	}
	
	public void joinGame(int gameId, String color) throws ServerException
	{
		JoinGameJSON join = new JoinGameJSON(gameId, color);
		URL url;
		try 
		{
			url = new URL(urlBase + "/games/join");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cookie", userCookie + gameCookie);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
						
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] send = new Gson().toJson(join).getBytes();
			writer.write(send);
			writer.close();
						
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String cookie = conn.getHeaderField("Set-cookie");
				cookie = cookie.replace("catan.game=", "");
				cookie = cookie.replace(";Path=/;", "");
				int gameID = Integer.parseInt(cookie);
				gameCookie = "; catan.game=" + gameID;
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
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
	
	public CatanModel getGameModel() throws ServerException
	{
		String response = (String) get("/game/model");
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel getGameModel(int modelNumber) throws ServerException 
	{
		String response = (String) get("/game/model?version=" + modelNumber);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			return null;
		}
		return null;
	}
	
	public CatanModel resetGame() throws ServerException
	{
		String response = (String) post("/game/reset", null);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel setCommands(JsonArray commands) throws ServerException
	{
		String response = (String) post("/game/commands", commands);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public JsonArray getCommands() throws ServerException
	{
		String response = (String) get("/game/commands");
		JsonArray json = new Gson().fromJson(response, JsonArray.class);
		return json;
	}
	
	public void addAI(String AIType) throws ServerException
	{
		AddAIJSON add = new AddAIJSON(AIType);
		post("/game/addAI", add);
	}
	
	public String[] listAI() throws ServerException
	{
		String response = (String) get("/game/listAI");
		JsonArray json = new Gson().fromJson(response, JsonArray.class);
		String[] AIs = new String[json.size()];
		for(int i = 0; i < json.size(); i++)
		{
			AIs[i] = json.get(i).getAsString();
		}
		return AIs;
	}
	
	public CatanModel sendChat(int playerIndex, String content) throws ServerException
	{
		SendChatJSON send = new SendChatJSON(playerIndex, content);
		String response = (String) post("/moves/sendChat", send);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel rollNumber(int playerIndex, int number) throws ServerException
	{
		RollNumberJSON data = new RollNumberJSON(playerIndex, number);
		String response = (String) post("/moves/rollNumber", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) throws ServerException
	{
		RobPlayerJSON data = new RobPlayerJSON(playerIndex, victimIndex, hexLocation);
		String response = (String) post("/moves/robPlayer", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel finishTurn(int playerIndex) throws ServerException
	{
		FinishTurnJSON data = new FinishTurnJSON(playerIndex);
		String response = (String) post("/moves/finishTurn", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel buyDevCard(int playerIndex) throws ServerException
	{
		BuyDevCardJSON data = new BuyDevCardJSON(playerIndex);
		String response = (String) post("/moves/buyDevCard", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) throws ServerException
	{
		YearOfPlentyJSON data = new YearOfPlentyJSON(playerIndex, resource1.toString(), resource2.toString());
		String response = (String) post("/moves/Year_of_Plenty", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws ServerException
	{
		RoadBuildingJSON data = new RoadBuildingJSON(playerIndex, spot1, spot2);
		String response = (String) post("/moves/Road_Building", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel soldier(int playerIndex, int victimIndex, HexLocation location) throws ServerException
	{
		SoldierJSON data = new SoldierJSON(playerIndex, victimIndex, location);
		String response = (String) post("/moves/Soldier", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel monopoly(int playerIndex, ResourceType resource) throws ServerException
	{
		MonopolyJSON data = new MonopolyJSON(playerIndex, resource.toString());
		String response = (String) post("/moves/Monopoly", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel monument(int playerIndex) throws ServerException
	{
		MonumentJSON data = new MonumentJSON(playerIndex);
		String response = (String) post("/moves/Monument", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) throws ServerException
	{
		BuildRoadJSON data = new BuildRoadJSON(playerIndex, roadLocation, free);
		String response = (String) post("/moves/buildRoad", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) throws ServerException
	{
		BuildSettlementJSON data = new BuildSettlementJSON(playerIndex, vertexLocation, free);
		String response = (String) post("/moves/buildSettlement", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel buildCity(int playerIndex, VertexLocation vertexLocation) throws ServerException
	{
		BuildCityJSON data = new BuildCityJSON(playerIndex, vertexLocation);
		String response = (String) post("/moves/buildCity", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel offerTrade(int playerIndex, int receiver, ResourceList offer) throws ServerException
	{
		OfferTradeJSON data = new OfferTradeJSON(playerIndex, offer, receiver);
		String response = (String) post("/moves/offerTrade", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel acceptTrade(int playerIndex, boolean willAccept) throws ServerException
	{
		AcceptTradeJSON data = new AcceptTradeJSON(playerIndex, willAccept);
		String response = (String) post("/moves/acceptTrade", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) throws ServerException
	{
		MaritimeTradeJSON data = new MaritimeTradeJSON(playerIndex, ratio, input.toString(), output.toString());
		String response = (String) post("/moves/maritimeTrade", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public CatanModel discardCards(int playerIndex, ResourceList discardedCards) throws ServerException
	{
		DiscardCardsJSON data = new DiscardCardsJSON(playerIndex, discardedCards);
		String response = (String) post("/moves/discardCards", data);
		CatanModel model;
		try 
		{
			model = JSONDeserializer.deserialize(response);
			return model;
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			e.printStackTrace();
		}
		return null;
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
			conn.setRequestProperty("Cookie", userCookie + gameCookie);
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
			conn.setRequestProperty("Cookie", userCookie + gameCookie);
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
