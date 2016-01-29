package test.server;

import java.util.List;

import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.communication.*;
import shared.definitions.CatanColor;

public class ProxyTestMain 
{
	public static void main(String[] args) 
	{
		RealProxy server = new RealProxy();
		try 
		{
			server.userLogin("test", "test");
			server.joinGame(3, CatanColor.RED);
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
			System.out.println("Create new game");
			CommGame game = server.createGame(true, true, true, "Automated Test");
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
		catch (ServerException e) 
		{
			e.printStackTrace();
		}
	}

}
