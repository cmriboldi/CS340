package test.server;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.JsonArray;

import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;

public class RealProxyTest 
{
	private static RealProxy server;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		server = new RealProxy();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		
	}

	@Before
	public void setUp() throws Exception 
	{
		server.userLogin("Pete", "pete");
		server.joinGame(0, CatanColor.ORANGE);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void userRegister() throws ServerException 
	{
		server.userRegister("JUnitTest", "junittest");
		assert(true);
	}
	
	@Test
	public void userLogin() throws ServerException 
	{
		server.userLogin("JUnitTest", "junittest");
		assert(true);
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
		server.createGame(true, true, true, "JUnitTest Game");
		assert(true);
	}
	
	@Test
	public void joinGame() throws ServerException 
	{
		server.joinGame(0, CatanColor.ORANGE);
		assert(true);
	}
	
	@Test
	public void saveGame() throws ServerException 
	{
		server.saveGame(0, "JUnit Test Save File");
		assert(true);
	}
	
	@Test
	public void loadGame() throws ServerException 
	{
		server.loadGame("JUnit Test Save File");
		assert(true);
	}
	
	@Test
	public void getGameModel() throws ServerException 
	{
		server.getGameModel(0);
		assert(true);
	}
	
	@Test
	public void getCommands() throws ServerException 
	{
		server.getCommands();
		assert(true);
	}
	
	@Test
	public void resetGame() throws ServerException 
	{
		server.resetGame();
		assert(true);
	}
	
	@Test
	public void setCommands() throws ServerException 
	{
		server.setCommands(new JsonArray());
		assert(true);
	}
	
	@Test
	public void addAi() throws ServerException 
	{
		server.addAI("This will not get a 200 no matter what I do");
		assert(true);
	}
	
	@Test
	public void listAI() throws ServerException 
	{
		server.listAI();
		assert(true);
	}
}
