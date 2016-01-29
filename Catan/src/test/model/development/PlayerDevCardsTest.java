package test.model.development;

import static org.junit.Assert.*;
import org.junit.*;
import model.development.PlayerDevCards;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

public class PlayerDevCardsTest
{
	private PlayerDevCards playerDevCards;
	
	@Before
	public void setUp() throws Exception
	{
		playerDevCards = new PlayerDevCards();
	}

	@After
	public void tearDown() throws Exception
	{
		playerDevCards = null;
	}

	@Test
	public void testEmptyPlayerCards()
	{
		assertEquals(4, playerDevCards.length());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(0).getDevCardCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(1).getDevCardCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(2).getDevCardCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(3).getDevCardCount());
	}
	
	@Test
	public void testAddPlayerCards()
	{
		for (int i = 0; i < 4; i++)
		{
			testAddingCardsToPlayer(i);
		}
	}
	
	@Test
	public void testRemovingPlayerCards()
	{
		for (int i = 0; i < 4; i++)
		{
			testAddingCardsToPlayer(i);
		}
		
		for (int i = 0; i < 4; i++)
		{
			testRemovingCardsFromPlayer(i);
		}
	}
	
	@Test
	public void testRemovingPlayerCardsFail()
	{	
		for (int i = 0; i < 4; i++)
		{
			testRemovingCardsFromPlayer(i);
		}
	}
	
	private void testAddingCardsToPlayer(int playerIndex)
	{
		playerDevCards.addCardToPlayer(DevCardType.MONOPOLY, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.MONUMENT, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.MONUMENT, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.ROAD_BUILD, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.SOLDIER, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		playerDevCards.addCardToPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		
		assertEquals(1, playerDevCards.getDevCardsForPlayer(playerIndex).getMonopolyCount());
		assertEquals(2, playerDevCards.getDevCardsForPlayer(playerIndex).getMonumentCount());
		assertEquals(1, playerDevCards.getDevCardsForPlayer(playerIndex).getRoadBuilderCount());
		assertEquals(1, playerDevCards.getDevCardsForPlayer(playerIndex).getSoldierCount());
		assertEquals(3, playerDevCards.getDevCardsForPlayer(playerIndex).getYearOfPlentyCount());
		assertEquals(8, playerDevCards.getDevCardsForPlayer(playerIndex).getDevCardCount());
	}
	
	private void testRemovingCardsFromPlayer(int playerIndex)
	{
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.MONOPOLY, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.MONUMENT, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.MONUMENT, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.ROAD_BUILD, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.SOLDIER, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			playerDevCards.removeCardFromPlayer(DevCardType.YEAR_OF_PLENTY, playerIndex);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getMonopolyCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getMonumentCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getRoadBuilderCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getSoldierCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getYearOfPlentyCount());
		assertEquals(0, playerDevCards.getDevCardsForPlayer(playerIndex).getDevCardCount());
	}

}
