package server.facade;

import com.google.gson.*;
import model.CatanModel;
import model.development.*;
import model.map.*;
import model.messagelog.*;
import model.players.*;
import model.resources.*;
import serverProxy.JSONDeserializer;
import shared.definitions.*;
import shared.exceptions.player.*;
import shared.locations.*;
import shared.communication.IdNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONSerializer {

	private static JSONSerializer _instance;
	
	private JSONSerializer(){}
	
	private static JSONSerializer instance() {
		
		if (_instance == null)
			_instance = new JSONSerializer();

		return _instance;
	}
	
	private ResourceManager resourceManager;
	private DevCardManager devCardManager;
	private PlayerManager playerManager;
	private MapManager mapManager;
	private ChatManager chatManager;
	
	private JsonObject catan;
	private JsonObject bank;
	private JsonObject chat;
	private JsonObject log;
	private JsonObject map;
	private JsonObject players;
	private JsonObject tradeOffer;
	private JsonObject turnTracker;
	
	
	private void setBank()
	{
		bank = new JsonObject();
		
		bank.addProperty("brick", 0);
		bank.addProperty("ore", 0);
		bank.addProperty("sheep", 0);
		bank.addProperty("wheat", 0);
		bank.addProperty("wood", 0);
	}
	
	private void setChat()
	{
		chat = new JsonObject();
		
		JsonArray lines = new JsonArray();		
		List<Line> list = chatManager.getChatMessages();
		for(Line l : list)
		{
			String message = l.getMessage();
			String source = l.getSource();
			
			JsonObject line = new JsonObject();
			line.addProperty("message", message);
			line.addProperty("source", source);
			
			lines.add(line);
		}		
		chat.add("lines", lines);
		
	}
	
	private void setLog()
	{
		log = new JsonObject();
		
		JsonArray lines = new JsonArray();
		List<Line> list = chatManager.getGameHistory();
		for(Line l : list)
		{
			String message = l.getMessage();
			String source = l.getSource();
			
			JsonObject line = new JsonObject();
			line.addProperty("message", message);
			line.addProperty("source", source);
			
			lines.add(line);
		}		
		log.add("lines", lines);
	}
	
	private void setMap()
	{
		map = new JsonObject();
		
		JsonArray hexes = new JsonArray();
		HashMap<HexLocation, Hex> hexesMap = mapManager.getHexes();
		for(Hex h : hexesMap.values())
		{
			JsonObject hex = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", h.location.getX());
			location.addProperty("y", h.location.getY());
			hex.add("location", location);
			hex.addProperty("resource", h.getResource().toString());
			hex.addProperty("number", h.getNumber());
						
			hexes.add(hex);
		}
		
		JsonArray ports = new JsonArray();
		HashMap<EdgeLocation, Port> portsMap = mapManager.getPorts();
		for(Port p : portsMap.values())
		{
			JsonObject port = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", p.location.getHexLoc().getX());
			location.addProperty("y", p.location.getHexLoc().getY());
			port.addProperty("resource", p.getType().toString());
			port.add("location", location);
			port.addProperty("direction", p.);
			port.addProperty("ratio", p.location.getDir().toString());
						
			hexes.add(hex);
		}
		
	}
	
	private String _serialize(CatanModel model)
	{
		this.resourceManager = model.resourceManager;
		this.devCardManager = model.cardManager;
		this.playerManager = model.playerManager;
		this.mapManager = model.mapManager;
		this.chatManager = model.chatManager;
		
		catan = new JsonObject();
		
		catan.add("bank", bank);
		catan.add("chat", chat);
		catan.add("log", log);
		catan.add("map", map);
		catan.add("players", players);
		catan.add("tradeOffer", tradeOffer);
		catan.add("turnTracker", turnTracker);
		
		return catan.getAsString();
	}
	
	public static String serialize(CatanModel model) throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException
	{
		return instance()._serialize(model);
	}
}
