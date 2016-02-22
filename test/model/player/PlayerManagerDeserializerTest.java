package test.model.player;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.development.DevCardManager;
import model.players.Player;
import model.players.PlayerManager;
import model.players.PlayerTurnTracker;
import serverProxy.JSONDeserializer;
import shared.definitions.DevCardType;
import test.TestJSON;

public class PlayerManagerDeserializerTest
{

	private PlayerManager playerManager; 
	private PlayerTurnTracker turnTracker;
	
	@Before
	public void setUp() throws Exception
	{
		CatanModel cm = JSONDeserializer.deserialize(TestJSON.get());
		playerManager = cm.playerManager; 
		turnTracker = playerManager.getTurnTracker(); 
		
	}

	@After
	public void tearDown() throws Exception
	{
		playerManager = null; 
		turnTracker = null; 
	}

	@Test
	public void testDeserializerPlayerManagerInitialization()
	{
		
		assertNotEquals(playerManager,null);
		assertNotEquals(turnTracker,null);
		assertNotEquals(playerManager.getCatanPlayers() ,null);
		assertEquals(playerManager.getCatanPlayers().length ,4);

	}
	
	
	@Test
	public void testDeserializerPlayerInitialization()
	{
		
		assertNotEquals(playerManager,null);
		assertNotEquals(turnTracker,null);
		assertNotEquals(playerManager.getCatanPlayers() ,null);
		assertEquals(playerManager.getCatanPlayers()[0].getCitiesRemaining() ,4);

	}
	
	
	public void testPlayer1()
	{
		System.out.println("Player 1 Testing"); 
		Player player1 = playerManager.getCatanPlayers()[0]; 
		assertEquals(player1.getRoadsRemaining(), 13); 
		assertEquals(player1.getCitiesRemaining(), 4); 
		assertEquals(player1.getSettlementsRemaining(), 3); 
		assertEquals(player1.getPoints(), 2); 
		assertEquals(player1.getPlayerIndex(), 0); 
		assertEquals(player1.getId(), 0); 
		assertEquals(player1.getName(), "Sam"); 

	}
	
	@Test
	public void testCanDoMethods()
	{
		
	}

}
