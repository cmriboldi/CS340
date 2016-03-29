package server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.resources.ResourceList;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import test.TestServer;

/**
 * TAs READ ME FIRST!!!
 *
 * Test the functionality of the Server Proxy for the client.
 * These tests are designed to check for connectivity with the server, not necessarily that the action run is correct.
 * The RealProxy is designed to complete a request only if the response from the server is a 200 code, any other response code
 * will result in a thrown exception.
 * Therefore, server.method(); assert(true); is still a valid test, because we will only reach the assert
 * statement if communication with the server was successful.
 *
 * This test initializes it's own server for the purposes of testing.  This server runs on the default port of 8081, having this
 * port already occupied may result in unexpected errors.
 */
public class RealProxyTest
{
	private static TestServer testServer;
	private static RealProxy server;

	/**
	 * Start the test server on port 8081 that the RealProxy will communicate with
	 *
	 * @throws Exception
     */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		System.out.println("here");
		testServer = new TestServer();
		System.out.println("---!!!!!!!!!!!!!!!!!!!!!!!!  Starting Server  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---");
		testServer.run();
		System.out.println("Server is running");
		server = new RealProxy();
		server.userLogin("Brooke", "brooke");
		server.joinGame(0, CatanColor.BLUE);
	}

	/**
	 * Stop the test server
	 * @throws Exception
     */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		testServer.stop();
		System.out.println("---!!!!!!!!!!!!!!!!!!!!!!!!  Stopping Server  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---");
	}

	@Test
	public void listGames() throws ServerException 
	{
		server.listGames();
		assert(true);
	}
	
	@Test
	public void createGame() throws ServerException 
	{
		server.createGame(false, false, false, "JUnitTest Game");
		assert(true);
	}
	
	@Test
	public void joinGame() throws ServerException 
	{
		server.joinGame(0, CatanColor.BLUE);
		assert(true);
	}
	
	@Test
	public void getGameModel() throws ServerException 
	{
		server.getGameModel();
		assert(true);
	}
	
	@Test
	public void getGameModelByVersion() throws ServerException 
	{
		server.getGameModel(2);
		assert(true);
	}

	@Test
	public void listAI() throws ServerException 
	{
		server.listAI();
		assert(true);
	}
	
	@Test
	public void sendChat() throws ServerException 
	{
		server.sendChat(1, "This is a JUnit test chat");
		assert(true);
	}
	
	@Test
	public void rollNumber() throws ServerException 
	{
		server.rollNumber(0, 10);
		assert(true);
	}
	
	@Test
	public void robPlayer() throws ServerException 
	{
		server.robPlayer(0, 1, new HexLocation(1,1));
		assert(true);
	}
	
	@Test
	public void finishTurn() throws ServerException 
	{
		server.finishTurn(0);
		assert(true);
	}
	
	@Test
	public void byuDevCard() throws ServerException 
	{
		server.buyDevCard(0);
		assert(true);
	}
	
	@Test
	public void year_of_plenty() throws ServerException 
	{
		server.yearOfPlenty(0, ResourceType.SHEEP, ResourceType.ORE);
		assert(true);
	}
	
	@Test
	public void road_building() throws ServerException 
	{
		server.roadBuilding(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.North), new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast));
		assert(true);
	}
	
	@Test
	public void soldier() throws ServerException 
	{
		server.soldier(0, 1, new HexLocation(1,1));
		assert(true);
	}
	
	@Test
	public void monopoly() throws ServerException 
	{
		server.monopoly(0, ResourceType.BRICK);
		assert(true);
	}
	
	@Test
	public void monument() throws ServerException 
	{
		server.monument(0);
		assert(true);
	}
	
	@Test
	public void buildRoad() throws ServerException 
	{
		server.buildRoad(0, new EdgeLocation(new HexLocation(1,1), EdgeDirection.North), true);
		assert(true);
	}
	
	@Test
	public void buildSettlement() throws ServerException 
	{
		server.buildSettlement(0, new VertexLocation(new HexLocation(1,1), VertexDirection.NorthEast), true);
		assert(true);
	}
	
	@Test
	public void buildCity() throws ServerException 
	{
		server.buildCity(0, new VertexLocation(new HexLocation(0,3), VertexDirection.NorthWest));
		assert(true);
	}
	
	@Test
	public void offerTrade() throws ServerException 
	{
		server.offerTrade(0, 1, new ResourceList(1,1,1,1,1));
		assert(true);
	}
	
	@Test
	public void acceptTrade() throws ServerException 
	{
		server.acceptTrade(1, false);
		assert(true);
	}
	
	@Test
	public void maritimeTrade() throws ServerException 
	{
		server.maritimeTrade(0, 3, ResourceType.ORE, ResourceType.BRICK);
		assert(true);
	}
	
	@Test
	public void discardCards() throws ServerException 
	{
		server.discardCards(0, new ResourceList(1,1,1,1,1));
		assert(true);
	}
}
