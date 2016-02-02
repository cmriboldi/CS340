package test.server;

import java.util.List;

import model.CatanModel;
import model.resources.ResourceList;
import serverProxy.JSONDeserializer;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.communication.*;
import shared.definitions.CatanColor;
import shared.definitions.LogLevel;
import shared.definitions.ResourceType;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import test.TestJSON;

public class ProxyTestMain 
{
	public static void main(String[] args)
	{
		RealProxy server = new RealProxy();
		try 
		{
			System.out.println("Register new user");
//			server.userRegister("test", "test");
			server.userLogin("Pete", "pete");
			
			System.out.println("Create new game");
			CommGame newGame = server.createGame(true, true, true, "Automated Test");
			System.out.println("Title: " + newGame.getTitle() + " id: " + newGame.getId() + " players:");
			CommPlayer[] playersArray = newGame.getPlayers();
			for(CommPlayer player : playersArray)
			{
				if(player != null)
				{
					System.out.println("\tColor: " + player.getColor() + " name: " + player.getName() + " id: " + player.getId());
				}
				else
				{
					System.out.println("\tEmpty Player spot");
				}
			}
			server.joinGame(0, CatanColor.ORANGE);
			server.getGameModel(1);
			List<CommGame> games = server.listGames();
			
			System.out.println("Print List of Games");
			for(CommGame game : games)
			{
				System.out.println("Title: " + game.getTitle() + " id: " + game.getId() + " players:");
				CommPlayer[] players = game.getPlayers();
				for(CommPlayer player : players)
				{
					if(player != null)
					{
						System.out.println("\tColor: " + player.getColor() + " name: " + player.getName() + " id: " + player.getId());
					}
					else
					{
						System.out.println("\tEmpty Player spot");
					}
				}
			}
			
			System.out.println("Saving current game");
			server.saveGame(3, "test save");
			System.out.println("Save Successful!");
			
			System.out.println("Loading test save game");
			server.loadGame("test save");
			System.out.println("Load Successful!");
			
			System.out.println("Get game model");
			server.getGameModel(1);
			
			System.out.println("Reset Game");
			server.resetGame();
			System.out.println("Reset Successful!");
			
//			System.out.println("Test Deserializer");
//			System.out.println(TestJSON.get());
//			CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
//			System.out.println("Well it didn't throw any exceptions");
			
//			System.out.println("Add AI");
//			server.addAI("Test that won't work anyway");
//			System.out.println("Added");
			
			System.out.println("List AIs");
			System.out.println(server.listAI().toString());
			
			System.out.println("Send Chat");
			server.sendChat(0, "Test Chat");
			System.out.println("Chat Successful");
			
			System.out.println("Roll Number");
			server.rollNumber(0, 10);
			System.out.println("Roll Number Successful");
			
			System.out.println("Rob Player");
			server.robPlayer(0, 1, new HexLocation(1,1));
			System.out.println("Rob Player Successful");
			
			System.out.println("Finish Turn");
			server.finishTurn(0);
			System.out.println("Finish Turn Successful!");
			
			System.out.println("Buy Dev Card");
			server.buyDevCard(1);
			System.out.println("Successful!");
			
			System.out.println("Year of Plenty");
			server.yearOfPlenty(1, ResourceType.ORE, ResourceType.BRICK);
			System.out.println("Successful!");
			
			System.out.println("Road Building");
			server.roadBuilding(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.South), new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast));
			System.out.println("Successful!");
			
			System.out.println("Soldier");
			server.soldier(0, 1, new HexLocation(1,1));
			System.out.println("Successful");
			
			System.out.println("Monoply");
			server.monopoly(0, ResourceType.ORE);
			System.out.println("Successful");
			
			System.out.println("Monumnet");
			server.monument(0);
			System.out.println("Successful");
			
			System.out.println("Build Road");
			server.buildRoad(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.South), false);
			System.out.println("Successful");
			
			System.out.println("Build Settlement");
			server.buildSettlement(0, new VertexLocation(new HexLocation(1,1), VertexDirection.East), false);
			System.out.println("Successful");
			
			System.out.println("Build City");
			server.buildCity(0, new VertexLocation(new HexLocation(1,1), VertexDirection.East));
			System.out.println("Successful");
			
			System.out.println("Offer Trade");
			server.offerTrade(0, 1, new ResourceList(1,1,1,1,1));
			System.out.println("Successful");
			
			System.out.println("Accept Trade");
			server.acceptTrade(1, true);
			System.out.println("Successful");
			
			System.out.println("Maritime Trade");
			server.maritimeTrade(0, 3, ResourceType.SHEEP, ResourceType.ORE);
			System.out.println("Successful");
			
			System.out.println("Discard Cards");
			server.discardCards(1, new ResourceList(1,1,1,1,1));
			System.out.println("Successful");
			
			System.out.println("Change Log Level");
			server.changeLogLevel(LogLevel.ALL);
			System.out.println("Successful!");
		} 
		catch (ServerException e) 
		{
			e.printStackTrace();
		}
	}

}
