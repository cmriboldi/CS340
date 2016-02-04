package test.model.player;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.development.DevCardManager;
import model.options.Options;
import model.players.Player;
import model.players.PlayerManager;
import model.players.PlayerTurnTracker;
import serverProxy.JSONDeserializer;
import shared.definitions.DevCardType;
import test.TestJSON;

public class PlayerCanDoTest
{

	private PlayerManager playerManager; 
	private PlayerTurnTracker turnTracker;
	Options options; 
	
	@Before
	public void setUp() throws Exception
	{
		CatanModel cm = JSONDeserializer.deserialize(TestJSON.get());
		playerManager = cm.playerManager; 
		turnTracker = playerManager.getTurnTracker(); 
		options = cm.options;
		
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
	public void testTurnTrackerInitialization()
	{
		
		assertNotEquals(playerManager,null);
		assertNotEquals(turnTracker,null);
		assertNotEquals(playerManager.getCatanPlayers() ,null);
		assertEquals(playerManager.getCatanPlayers()[0].getCitiesRemaining() ,4);
		assertEquals(playerManager.getTurnTracker().getTurnIndex(), 0); 
		assertNotEquals(playerManager.getTurnTracker().getTurnIndex(), 1); 
		assertNotEquals(playerManager.getTurnTracker().getTurnIndex(), -1); 
		assertTrue(playerManager.getTurnTracker().getStatus().equals("Rolling")); 
		assertFalse(playerManager.getTurnTracker().getStatus().equals("rolling")); 
		
		//turnTracker": {
        //"status": "Rolling",
        //"currentTurn": 0,
        //"longestRoad": -1,
        //"largestArmy": -1

	}
	
	@Test
	public void canPlay()
	{
		boolean player1CanPlay = options.canPlay(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player1CanPlay); // can't play because statusis rolling
	}
	

	
	@Test
	public void testCanDoMethods()
	{
		
	}

}
