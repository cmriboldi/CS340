package test.model.development;

import static org.junit.Assert.*;
import org.junit.*;

import model.development.*;
import serverProxy.JSONDeserializer;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

public class DevCardManagerTest
{
	private DevCardManager devManager;

	@Before
	public void setUp()
	{
//		CatanModel cm = JSONDeserializer.dezerialize(TestJSON.get());
		
//		devManager = cm.devManager;
		devManager = new DevCardManager();
	}

	@After
	public void tearDown()
	{
		devManager = null;
	}

	@Test
	public void testDrawCard()
	{
		DevCardList newStack = new DevCardList();
		newStack.setMonopoly(4);
		newStack.setMonument(4);
		newStack.setRoadBuilder(4);
		newStack.setSoldier(4);
		newStack.setYearOfPlenty(4);
		devManager.setDevCardStack(newStack);

		for (int i = 0; i < 4; i++)
		{
			testDrawCardForPlayer(i);
		}
	}

	private void testDrawCardForPlayer(int playerIndex)
	{
		DevCardList oldStack = devManager.getDevCardStack();
		int oldStackSize = oldStack.getDevCardCount();
		
		int oldMonopolyCount = oldStack.getMonopolyCount();
		int oldMonumentCount = oldStack.getMonumentCount();
		int oldRoadBuilderCount = oldStack.getRoadBuilderCount();
		int oldSoldierCount = oldStack.getSoldierCount();
		int oldYearOfPlentyCount = oldStack.getYearOfPlentyCount();

		try
		{
			if (devManager.canDrawDevCard())
			{
				assertEquals(0,devManager.getNewDevCards().getDevCardsForPlayer(playerIndex).getDevCardCount());
				DevCardType devCard = devManager.drawCard(playerIndex);
				assertTrue(devManager.getNewDevCards().hasDevCard(playerIndex, devCard));
				assertEquals(1,devManager.getNewDevCards().getDevCardsForPlayer(playerIndex).getDevCardCount());

				assertEquals(oldStackSize - 1, devManager.getDevCardStack().getDevCardCount());
				
				switch (devCard)
				{
				case MONOPOLY:
					System.out.println("The player drew a monopoly card.");
					assertEquals(oldMonopolyCount - 1, devManager.getDevCardStack().getCardTypeCount(devCard));
					break;
				case MONUMENT:
					System.out.println("The player drew a monument card.");
					assertEquals(oldMonumentCount - 1, devManager.getDevCardStack().getCardTypeCount(devCard));
					break;
				case ROAD_BUILD:
					System.out.println("The player drew a road builder card.");
					assertEquals(oldRoadBuilderCount - 1, devManager.getDevCardStack().getCardTypeCount(devCard));
					break;
				case SOLDIER:
					System.out.println("The player drew a soldier card.");
					assertEquals(oldSoldierCount - 1, devManager.getDevCardStack().getCardTypeCount(devCard));
					break;
				case YEAR_OF_PLENTY:
					System.out.println("The player drew a year of plenty card.");
					assertEquals(oldYearOfPlentyCount - 1, devManager.getDevCardStack().getCardTypeCount(devCard));
					break;
				}
				
			}
		} catch (NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
	}

}
