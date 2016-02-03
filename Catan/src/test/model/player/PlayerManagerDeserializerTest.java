package test.model.player;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.development.DevCardManager;
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
	public void testDeserializerInitialization()
	{
		
		assertNotEquals(playerManager,null);
		assertNotEquals(turnTracker,null);
		assertNotEquals(playerManager.getCatanPlayers() ,null);
		assertEquals(playerManager.getCatanPlayers().length ,4);




		
/*		
		//assert values are not null
		assertNotEquals(devManager,null);
		assertNotEquals(devManager.getDevCardStack(),null);
		assertNotEquals(devManager.getNewDevCards(),null);
		assertNotEquals(devManager.getOldDevCards(),null);
		assertNotEquals(devManager.getPlayedDevCards(),null);
		
		//assert devManager elements sizes are correct
		assertEquals(devManager.getDevCardStack().getDevCardCount(),25);
		assertEquals(devManager.getNewDevCards().length(),4);
		assertEquals(devManager.getOldDevCards().length(),4);
		assertEquals(devManager.getPlayedDevCards().length(),4);
		
		//assert hasPlayedDevCard values are all false.
		assertEquals(devManager.hasPlayedDevCard(0),false);
		assertEquals(devManager.hasPlayedDevCard(1),false);
		assertEquals(devManager.hasPlayedDevCard(2),false);
		assertEquals(devManager.hasPlayedDevCard(3),false);*/
	}
	
	@Test
	public void testCanDoMethods()
	{
		//assert values are not null
		//assertEquals(devManager.canDrawDevCard(), true);
		for (int i = 0; i < 4; i++)
		{
		//	assertEquals(devManager.canPlayDevCard(i, DevCardType.MONOPOLY), false);
		//	assertEquals(devManager.canPlayDevCard(i, DevCardType.MONUMENT), false);
		//	assertEquals(devManager.canPlayDevCard(i, DevCardType.ROAD_BUILD), false);
		//	assertEquals(devManager.canPlayDevCard(i, DevCardType.SOLDIER), false);
		//	assertEquals(devManager.canPlayDevCard(i, DevCardType.YEAR_OF_PLENTY), false);
		}
	}

}
