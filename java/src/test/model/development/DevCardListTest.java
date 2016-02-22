package test.model.development;

import static org.junit.Assert.*;
import org.junit.*;
import model.development.DevCardList;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

public class DevCardListTest
{
	private DevCardList devCardList;

	@Before
	public void setUp() throws Exception
	{
		
		devCardList = new DevCardList();
	}

	@After
	public void tearDown() throws Exception
	{
		devCardList = null;
	}

	@Test
	public void testDevCardListIsEmpty()
	{
		assertEquals(0, devCardList.getDevCardCount());
		assertEquals(0, devCardList.getMonopolyCount());
		assertEquals(0, devCardList.getMonumentCount());
		assertEquals(0, devCardList.getRoadBuilderCount());
		assertEquals(0, devCardList.getSoldierCount());
		assertEquals(0, devCardList.getYearOfPlentyCount());
		
		assertFalse(devCardList.hasDevCard(DevCardType.MONOPOLY));
		assertFalse(devCardList.hasDevCard(DevCardType.MONUMENT));
		assertFalse(devCardList.hasDevCard(DevCardType.ROAD_BUILD));
		assertFalse(devCardList.hasDevCard(DevCardType.SOLDIER));
		assertFalse(devCardList.hasDevCard(DevCardType.YEAR_OF_PLENTY));
	}

	@Test
	public void testAddDevCard()
	{
		devCardList.addDevCard(DevCardType.MONOPOLY);
		devCardList.addDevCard(DevCardType.MONUMENT);
		devCardList.addDevCard(DevCardType.MONUMENT);
		devCardList.addDevCard(DevCardType.ROAD_BUILD);
		devCardList.addDevCard(DevCardType.SOLDIER);
		devCardList.addDevCard(DevCardType.YEAR_OF_PLENTY);

		assertEquals(6, devCardList.getDevCardCount());
		assertEquals(1, devCardList.getMonopolyCount());
		assertEquals(2, devCardList.getMonumentCount());
		assertEquals(1, devCardList.getRoadBuilderCount());
		assertEquals(1, devCardList.getSoldierCount());
		assertEquals(1, devCardList.getYearOfPlentyCount());
	}

	@Test
	public void testRemoveDevCard()
	{
		testAddDevCard();

		try
		{
			devCardList.removeDevCard(DevCardType.MONOPOLY);
			devCardList.removeDevCard(DevCardType.MONUMENT);
			devCardList.removeDevCard(DevCardType.MONUMENT);
			devCardList.removeDevCard(DevCardType.ROAD_BUILD);
			devCardList.removeDevCard(DevCardType.SOLDIER);
			devCardList.removeDevCard(DevCardType.YEAR_OF_PLENTY);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		assertEquals(0, devCardList.getDevCardCount());
		assertEquals(0, devCardList.getMonopolyCount());
		assertEquals(0, devCardList.getMonumentCount());
		assertEquals(0, devCardList.getRoadBuilderCount());
		assertEquals(0, devCardList.getSoldierCount());
		assertEquals(0, devCardList.getYearOfPlentyCount());
	}
	
	@Test
	public void testRemoveDevCardFailure()
	{
		try
		{
			devCardList.removeDevCard(DevCardType.MONOPOLY);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			devCardList.removeDevCard(DevCardType.MONUMENT);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			devCardList.removeDevCard(DevCardType.ROAD_BUILD);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			devCardList.removeDevCard(DevCardType.SOLDIER);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			devCardList.removeDevCard(DevCardType.YEAR_OF_PLENTY);
		} catch (NotEnoughDevCardsException e)
		{
			System.out.println(e.getMessage());
		}

		assertEquals(0, devCardList.getDevCardCount());
		assertEquals(0, devCardList.getMonopolyCount());
		assertEquals(0, devCardList.getMonumentCount());
		assertEquals(0, devCardList.getRoadBuilderCount());
		assertEquals(0, devCardList.getSoldierCount());
		assertEquals(0, devCardList.getYearOfPlentyCount());
	}
	
	@Test
	public void testHasDevCard()
	{
		
		testDevCardListIsEmpty();
		
		assertFalse(devCardList.hasDevCard(DevCardType.MONOPOLY));
		devCardList.addDevCard(DevCardType.MONOPOLY);
		assertTrue(devCardList.hasDevCard(DevCardType.MONOPOLY));
		
		assertFalse(devCardList.hasDevCard(DevCardType.MONUMENT));
		devCardList.addDevCard(DevCardType.MONUMENT);
		assertTrue(devCardList.hasDevCard(DevCardType.MONUMENT));
		
		assertFalse(devCardList.hasDevCard(DevCardType.ROAD_BUILD));
		devCardList.addDevCard(DevCardType.ROAD_BUILD);
		assertTrue(devCardList.hasDevCard(DevCardType.ROAD_BUILD));
		
		assertFalse(devCardList.hasDevCard(DevCardType.SOLDIER));
		devCardList.addDevCard(DevCardType.SOLDIER);
		assertTrue(devCardList.hasDevCard(DevCardType.SOLDIER));
		
		assertFalse(devCardList.hasDevCard(DevCardType.YEAR_OF_PLENTY));
		devCardList.addDevCard(DevCardType.YEAR_OF_PLENTY);
		assertTrue(devCardList.hasDevCard(DevCardType.YEAR_OF_PLENTY));
		
		assertEquals(5, devCardList.getDevCardCount());
		assertEquals(1, devCardList.getMonopolyCount());
		assertEquals(1, devCardList.getMonumentCount());
		assertEquals(1, devCardList.getRoadBuilderCount());
		assertEquals(1, devCardList.getSoldierCount());
		assertEquals(1, devCardList.getYearOfPlentyCount());
	}

}
