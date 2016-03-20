package test.model.player;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.options.Options;
import model.players.PlayerManager;
import model.players.PlayerTurnTracker;
import serverProxy.JSONDeserializer;
import shared.definitions.TurnType;
import shared.locations.HexLocation;
import serverProxy.JsonLoader;
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
		
		
		String json = JsonLoader.readFile("json/default.json");
        CatanModel cm2 = JSONDeserializer.deserialize(json);
        
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
		//turnTracker": {
        //"status": "Rolling",
        //"currentTurn": 0,
        //"longestRoad": -1,
        //"largestArmy": -1
		
		assertNotEquals(playerManager,null);
		assertNotEquals(turnTracker,null);
		assertNotEquals(playerManager.getCatanPlayers() ,null);
		assertEquals(playerManager.getCatanPlayers()[0].getCitiesRemaining() ,4);
		assertEquals(playerManager.getTurnTracker().getTurnIndex(), 0); 
		assertNotEquals(playerManager.getTurnTracker().getTurnIndex(), 1); 
		assertNotEquals(playerManager.getTurnTracker().getTurnIndex(), -1); 
		assertTrue(playerManager.getTurnTracker().getStatus() == TurnType.ROLLING);

	}
	
	@Test
	public void testCanPlayDuringRolling()
	{
		boolean player0CanPlay = options.canPlay(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanPlay); // can't play because status is currently rolling
		boolean player1CanPlay = options.canPlay(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanPlay); 
		boolean player2CanPlay = options.canPlay(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanPlay);
		boolean player3CanPlay = options.canPlay(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanPlay);
	}
	
	@Test
	public void testCanRollDuringRolling()
	{
		boolean player0CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertTrue(player0CanRoll);  
		boolean player1CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanRoll);  
		boolean player2CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanRoll); 
		boolean player3CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanRoll); 
	}
	
	@Test
	public void canDiscardCardsDuringRolling()
	{
		boolean player0CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanDiscard);  
		boolean player1CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanDiscard);  
		boolean player2CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanDiscard); 
		boolean player3CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanDiscard); 
	}
	
	@Test
	public void testCanFinishTurnDuringRolling()
	{
		boolean player0CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanFinish);  
		boolean player1CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanFinish);  
		boolean player2CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanFinish); 
		boolean player3CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanFinish); 
	}
	
	@Test
	public void testCanPlaceRobberDuringRolling()
	{
		boolean player0CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player0CanPlaceRobber);  
		boolean player1CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player1CanPlaceRobber);  
		boolean player2CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player2CanPlaceRobber); 
		boolean player3CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player3CanPlaceRobber); 
	}
	
	
	
	
	// --------------------------------------------------------------------------- // 
	
	
	@Test
	public void testCanPlayDuringPlayForPlayer1()
	{
		turnTracker.setStatus(TurnType.PLAYING);
		turnTracker.setTurnIndex(1);
		boolean player0CanPlay = options.canPlay(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanPlay); // can't play because status is currently rolling
		boolean player1CanPlay = options.canPlay(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertTrue(player1CanPlay); 
		boolean player2CanPlay = options.canPlay(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanPlay);
		boolean player3CanPlay = options.canPlay(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanPlay);
	}
	
	@Test
	public void testCanRollDuringPlayForPlayer1()
	{
		turnTracker.setStatus(TurnType.PLAYING);
		turnTracker.setTurnIndex(1);
		
		boolean player0CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanRoll);  
		boolean player1CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanRoll);  
		boolean player2CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanRoll); 
		boolean player3CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanRoll); 
	}
	
	
	@Test
	public void canDiscardCardsDuringPlayForPlayer1()
	{
		turnTracker.setStatus(TurnType.PLAYING);
		turnTracker.setTurnIndex(1);
		
		boolean player0CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanDiscard);  
		boolean player1CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertFalse(player1CanDiscard);  
		boolean player2CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanDiscard); 
		boolean player3CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanDiscard); 
	}
	
	
	
	@Test
	public void testCanFinishTurnDuringPlayForPlayer1()
	{
		turnTracker.setStatus(TurnType.PLAYING);
		turnTracker.setTurnIndex(1);
		
		boolean player0CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[0].getPlayerIndex());
		assertFalse(player0CanFinish);  
		boolean player1CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[1].getPlayerIndex());
		assertTrue(player1CanFinish);  
		boolean player2CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[2].getPlayerIndex());
		assertFalse(player2CanFinish); 
		boolean player3CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[3].getPlayerIndex());
		assertFalse(player3CanFinish); 
	}
	
	
	
	@Test
	public void testCanPlaceRobberDuringPlayForPlayer1()
	{
		turnTracker.setStatus(TurnType.PLAYING);
		turnTracker.setTurnIndex(1);
		
		boolean player0CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player0CanPlaceRobber);  
		boolean player1CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player1CanPlaceRobber);  
		boolean player2CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player2CanPlaceRobber); 
		boolean player3CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
		assertFalse(player3CanPlaceRobber); 
	}
	
	
	// --------------------------------------------------------------------------- // 
	
	
		@Test
		public void testCanPlayDuringRobbingForPlayer2()
		{
			turnTracker.setStatus(TurnType.ROBBING);
			turnTracker.setTurnIndex(2);
			boolean player0CanPlay = options.canPlay(playerManager.getCatanPlayers()[0].getPlayerIndex());
			assertFalse(player0CanPlay); // can't play because status is currently rolling
			boolean player1CanPlay = options.canPlay(playerManager.getCatanPlayers()[1].getPlayerIndex());
			assertFalse(player1CanPlay); 
			boolean player2CanPlay = options.canPlay(playerManager.getCatanPlayers()[2].getPlayerIndex());
			assertFalse(player2CanPlay);
			boolean player3CanPlay = options.canPlay(playerManager.getCatanPlayers()[3].getPlayerIndex());
			assertFalse(player3CanPlay);
		}
		
		@Test
		public void testCanRollDuringRobbingForPlayer2()
		{
			turnTracker.setStatus(TurnType.ROBBING);
			turnTracker.setTurnIndex(2);
			
			boolean player0CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[0].getPlayerIndex());
			assertFalse(player0CanRoll);  
			boolean player1CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[1].getPlayerIndex());
			assertFalse(player1CanRoll);  
			boolean player2CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[2].getPlayerIndex());
			assertFalse(player2CanRoll); 
			boolean player3CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[3].getPlayerIndex());
			assertFalse(player3CanRoll); 
		}
		
		
		@Test
		public void canDiscardCardsDuringRobbingForPlayer2()
		{
			turnTracker.setStatus(TurnType.ROBBING);
			turnTracker.setTurnIndex(2);
			
			boolean player0CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[0].getPlayerIndex());
			assertFalse(player0CanDiscard);  
			boolean player1CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[1].getPlayerIndex());
			assertFalse(player1CanDiscard);  
			boolean player2CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[2].getPlayerIndex());
			assertFalse(player2CanDiscard); 
			boolean player3CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[3].getPlayerIndex());
			assertFalse(player3CanDiscard); 
		}
		
		
		
		@Test
		public void testCanFinishTurnDuringRobbingForPlayer2()
		{
			turnTracker.setStatus(TurnType.ROBBING);
			turnTracker.setTurnIndex(2);
			
			boolean player0CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[0].getPlayerIndex());
			assertFalse(player0CanFinish);  
			boolean player1CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[1].getPlayerIndex());
			assertFalse(player1CanFinish);  
			boolean player2CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[2].getPlayerIndex());
			assertFalse(player2CanFinish); 
			boolean player3CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[3].getPlayerIndex());
			assertFalse(player3CanFinish); 
		}
		
		
		
		@Test
		public void testCanPlaceRobberDuringRobbingForPlayer2()
		{
			turnTracker.setStatus(TurnType.ROBBING);
			turnTracker.setTurnIndex(2);
			
			boolean player0CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
			assertFalse(player0CanPlaceRobber);  
			boolean player1CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
			assertFalse(player1CanPlaceRobber);  
			boolean player2CanPlaceRobber = options.canPlaceRobber(new HexLocation(0,0));
			assertTrue(player2CanPlaceRobber); 
			boolean player3CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
			assertFalse(player3CanPlaceRobber); 
		}
	

		// --------------------------------------------------------------------------- // 
		
		
			@Test
			public void testCanPlayDuringDiscardingForPlayer3()
			{
				
				turnTracker.setStatus(TurnType.DISCARDING);
				turnTracker.setTurnIndex(3);
				boolean player0CanPlay = options.canPlay(playerManager.getCatanPlayers()[0].getPlayerIndex());
				assertFalse(player0CanPlay); // can't play because status is currently rolling
				boolean player1CanPlay = options.canPlay(playerManager.getCatanPlayers()[1].getPlayerIndex());
				assertFalse(player1CanPlay); 
				boolean player2CanPlay = options.canPlay(playerManager.getCatanPlayers()[2].getPlayerIndex());
				assertFalse(player2CanPlay);
				boolean player3CanPlay = options.canPlay(playerManager.getCatanPlayers()[3].getPlayerIndex());
				assertFalse(player3CanPlay);
			}
			
			@Test
			public void testCanRollDuringDiscardingForPlayer3()
			{
				turnTracker.setStatus(TurnType.DISCARDING);
				turnTracker.setTurnIndex(3);
				
				boolean player0CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[0].getPlayerIndex());
				assertFalse(player0CanRoll);  
				boolean player1CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[1].getPlayerIndex());
				assertFalse(player1CanRoll);  
				boolean player2CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[2].getPlayerIndex());
				assertFalse(player2CanRoll); 
				boolean player3CanRoll = options.canRollNumber(playerManager.getCatanPlayers()[3].getPlayerIndex());
				assertFalse(player3CanRoll); 
			}
			
			
			@Test
			public void canDiscardCardsDuringDiscardingForPlayer3()
			{
				turnTracker.setStatus(TurnType.DISCARDING);
				turnTracker.setTurnIndex(3);
				
				boolean player0CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[0].getPlayerIndex());
				assertTrue(player0CanDiscard);  
				boolean player1CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[1].getPlayerIndex());
				assertTrue(player1CanDiscard);  
				boolean player2CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[2].getPlayerIndex());
				assertTrue(player2CanDiscard); 
				boolean player3CanDiscard = options.canDiscardCards(playerManager.getCatanPlayers()[3].getPlayerIndex());
				assertTrue(player3CanDiscard); 
			}
			
			
			
			@Test
			public void testCanFinishTurnDuringDiscardingForPlayer3()
			{
				turnTracker.setStatus(TurnType.DISCARDING);
				turnTracker.setTurnIndex(3);
				
				boolean player0CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[0].getPlayerIndex());
				assertFalse(player0CanFinish);  
				boolean player1CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[1].getPlayerIndex());
				assertFalse(player1CanFinish);  
				boolean player2CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[2].getPlayerIndex());
				assertFalse(player2CanFinish); 
				boolean player3CanFinish = options.canFinishTurn(playerManager.getCatanPlayers()[3].getPlayerIndex());
				assertFalse(player3CanFinish); 
			}
			
			
			
			@Test
			public void testCanPlaceRobberDuringDiscardingForPlayer3()
			{
				turnTracker.setStatus(TurnType.DISCARDING);
				turnTracker.setTurnIndex(3);
				
				boolean player0CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
				assertFalse(player0CanPlaceRobber);  
				boolean player1CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
				assertFalse(player1CanPlaceRobber);  
				boolean player2CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
				assertFalse(player2CanPlaceRobber); 
				boolean player3CanPlaceRobber = options.canPlaceRobber(new HexLocation(2,3));
				assertFalse(player3CanPlaceRobber); 
			}
		

}
