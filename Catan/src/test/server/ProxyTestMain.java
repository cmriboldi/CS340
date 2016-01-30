package test.server;

import java.util.List;

import model.CatanModel;
import serverProxy.JSONDeserializer;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.communication.*;
import shared.definitions.CatanColor;
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
			
			System.out.println("Test Deserializer");
			System.out.println(TestJSON.get());
			CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
			System.out.println("Well it didn't throw any exceptions");
		} 
		catch (ServerException e) 
		{
			e.printStackTrace();
		}
	}

}
