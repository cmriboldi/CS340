package test.model.options;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.CatanModel;
import model.options.Options;
import model.resources.ResourceList;
import serverProxy.JSONDeserializer;
import shared.definitions.Cost;
import shared.definitions.DevCardType;
import shared.exceptions.resources.NotEnoughBankResourcesException;
import shared.locations.*;
import test.JsonFileLoader;

public class OptionsTest
{

	private CatanModel cm;
	private Options options;
	
	@Before
	public void setUp() throws Exception
	{
		String json = JsonFileLoader.readFile("json/empty.json");
        cm = JSONDeserializer.deserialize(json);
        options = cm.options;
	}

	@After
	public void tearDown() throws Exception
	{
		cm = null;
		options = null;
	}

	@Test
	public void testCanAffordMethods() throws Exception
	{	
		ResourceList[] newPlayerResources = new ResourceList[4];
		for(int i = 0; i < 4; i++)
		{
			assertEquals(options.canAffordCity(i), false);
			assertEquals(options.canAffordRoad(i), false);
			assertEquals(options.canAffordTown(i), false);
			assertEquals(options.canAffordDevCard(i), false);
			
		}
		
		newPlayerResources[0] = Cost.CITY.getCost();
		newPlayerResources[1] = Cost.ROAD.getCost();
		newPlayerResources[2] = Cost.SETTLEMENT.getCost();
		newPlayerResources[3] = Cost.DEVCARD.getCost();
		
		try
		{
			cm.resourceManager.payOutResources(newPlayerResources);
		} catch (NotEnoughBankResourcesException e)
		{
			System.out.println("Exeption: " + e.getMessage());
			e.printStackTrace();
		}
		
		assertEquals(options.canAffordCity(0), true);
		assertEquals(options.canAffordRoad(1), true);
		assertEquals(options.canAffordTown(2), true);
		assertEquals(options.canAffordDevCard(3), true);
		
		assertEquals(options.canAffordRoad(0), false);
		assertEquals(options.canAffordTown(0), false);
		assertEquals(options.canAffordDevCard(0), false);
		
		assertEquals(options.canAffordCity(1), false);
		assertEquals(options.canAffordTown(1), false);
		assertEquals(options.canAffordDevCard(1), false);
		
		assertEquals(options.canAffordCity(2), false);
		assertEquals(options.canAffordRoad(2), true);
		assertEquals(options.canAffordDevCard(2), false);
		
		assertEquals(options.canAffordCity(3), false);
		assertEquals(options.canAffordRoad(3), false);
		assertEquals(options.canAffordTown(3), false);
	}
	
	@Test
	public void testCanDevCardMethods()
	{	
		ResourceList[] newPlayerResources = new ResourceList[4];
		
		for(int i = 1; i < 4; i++)
		{
			assertEquals(options.hasDevCard(i, DevCardType.MONOPOLY), false);
			assertEquals(options.hasDevCard(i, DevCardType.MONUMENT), false);
			assertEquals(options.hasDevCard(i, DevCardType.ROAD_BUILD), false);
			assertEquals(options.hasDevCard(i, DevCardType.SOLDIER), false);
			assertEquals(options.hasDevCard(i, DevCardType.YEAR_OF_PLENTY), false);
		}
		
		newPlayerResources[0] = Cost.DEVCARD.getCost();
		newPlayerResources[1] = new ResourceList();
		newPlayerResources[2] = Cost.DEVCARD.getCost();
		newPlayerResources[3] = new ResourceList();
		
		try
		{
			cm.resourceManager.payOutResources(newPlayerResources);
		} catch (NotEnoughBankResourcesException e)
		{
			System.out.println("Exeption: " + e.getMessage());
			e.printStackTrace();
		}
		
		assertEquals(options.canBuyDevCard(0), true);
		assertEquals(options.canBuyDevCard(1), false);
		assertEquals(options.canBuyDevCard(2), false);
		assertEquals(options.canBuyDevCard(3), false);
		
		assertEquals(options.canDrawDevCard(0), true);
		assertEquals(options.canDrawDevCard(1), false);
		assertEquals(options.canDrawDevCard(2), false);
		assertEquals(options.canDrawDevCard(3), false);
		
		assertEquals(options.canUseMonopoly(0), true);
		assertEquals(options.canUseMonument(0), true);
		assertEquals(options.canUseRoadBuilder(0), true);
		assertEquals(options.canUseSoldier(0), true);
		assertEquals(options.canUseYearOfPlenty(0), false);
		
		for (int i = 1; i < 4; i++)
		{
			assertEquals(options.canUseMonopoly(i), false);
			assertEquals(options.canUseMonument(i), false);
			assertEquals(options.canUseRoadBuilder(i), false);
			assertEquals(options.canUseSoldier(i), false);
			assertEquals(options.canUseYearOfPlenty(i), false);
		}
		
	}
	
	@Test
	public void testCanTradeMethods()
	{	
		assertEquals(options.canOfferTrade(0,1), true);
		assertEquals(options.canOfferTrade(0,2), true);
		assertEquals(options.canOfferTrade(0,3), true);
		
		assertEquals(options.canOfferTrade(1,0), true);
		assertEquals(options.canOfferTrade(1,2), false);
		assertEquals(options.canOfferTrade(1,3), false);
		
		assertEquals(options.canOfferTrade(2,0), true);
		assertEquals(options.canOfferTrade(2,1), false);
		assertEquals(options.canOfferTrade(2,3), false);
		
		assertEquals(options.canOfferTrade(3,0), true);
		assertEquals(options.canOfferTrade(3,1), false);
		assertEquals(options.canOfferTrade(3,2), false);
		
		assertEquals(options.canMaritimeTrade(0), true);
		assertEquals(options.canMaritimeTrade(1), true);
		assertEquals(options.canMaritimeTrade(2), false);
		assertEquals(options.canMaritimeTrade(3), false);
		
	}
	
	@Test
	public void testCanPlayMethods()
	{	
		assertEquals(options.canPlay(0), true);
		assertEquals(options.canPlay(1), false);
		assertEquals(options.canPlay(2), false);
		assertEquals(options.canPlay(3), false);
		
		//Test canRoll
		assertEquals(options.canRollNumber(0), false);
		assertEquals(options.canRollNumber(1), false);
		assertEquals(options.canRollNumber(2), false);
		assertEquals(options.canRollNumber(3), false);
		
		cm.playerManager.setStatus("Rolling");
		
		assertEquals(options.canRollNumber(0), true);
		assertEquals(options.canRollNumber(1), false);
		assertEquals(options.canRollNumber(2), false);
		assertEquals(options.canRollNumber(3), false);
		
		//Test canRob
		assertEquals(options.canPlaceRobber(0), false);
		assertEquals(options.canPlaceRobber(1), false);
		assertEquals(options.canPlaceRobber(2), false);
		assertEquals(options.canPlaceRobber(3), false);
		
		cm.playerManager.setStatus("Robbing");
		
		assertEquals(options.canPlaceRobber(0), true);
		assertEquals(options.canPlaceRobber(1), false);
		assertEquals(options.canPlaceRobber(2), false);
		assertEquals(options.canPlaceRobber(3), false);
		
		//Test canDiscard
		assertEquals(options.canDiscardCards(0), false);
		assertEquals(options.canDiscardCards(1), false);
		assertEquals(options.canDiscardCards(2), false);
		assertEquals(options.canDiscardCards(3), false);
		
		cm.playerManager.setStatus("Discarding");
		
		assertEquals(options.canDiscardCards(0), true);
		assertEquals(options.canDiscardCards(1), true);
		assertEquals(options.canDiscardCards(2), true);
		assertEquals(options.canDiscardCards(3), true);
		
		//Test canFinishTurn
		assertEquals(options.canFinishTurn(0), false);
		assertEquals(options.canFinishTurn(1), false);
		assertEquals(options.canFinishTurn(2), false);
		assertEquals(options.canFinishTurn(3), false);
		
		cm.playerManager.setStatus("Playing");
		
		assertEquals(options.canFinishTurn(0), true);
		assertEquals(options.canFinishTurn(1), false);
		assertEquals(options.canFinishTurn(2), false);
		assertEquals(options.canFinishTurn(3), false);
	}
	
	@Test
	public void testCanPlaceMethods()
	{
		HexLocation hexLocation = new HexLocation(0,2);
		VertexLocation pieceVertexLocation1 = new VertexLocation(hexLocation, VertexDirection.NorthEast);
		
		assertEquals(options.canPlaceTown(0, pieceVertexLocation1),false);
		assertEquals(options.canPlaceTown(1, pieceVertexLocation1),false);
		assertEquals(options.canPlaceTown(2, pieceVertexLocation1),false);
		assertEquals(options.canPlaceTown(3, pieceVertexLocation1),false);
		
		VertexLocation pieceVertexLocation2 = new VertexLocation(hexLocation, VertexDirection.West);
		
		assertEquals(options.canPlaceTown(0, pieceVertexLocation2),false);
		assertEquals(options.canPlaceTown(1, pieceVertexLocation2),false);
		assertEquals(options.canPlaceTown(2, pieceVertexLocation2),false);
		assertEquals(options.canPlaceTown(3, pieceVertexLocation2),false);
		
		EdgeLocation pieceEdgeLocation = new EdgeLocation(hexLocation, EdgeDirection.NorthWest);
		
		assertEquals(options.canPlaceRoad(0, pieceEdgeLocation),false); //nft
		
		cm.mapManager.placeRoad(pieceEdgeLocation, 0);
		
		assertEquals(options.canPlaceRoad(0, pieceEdgeLocation),false); //nft
		
		assertEquals(options.canPlaceTown(0, pieceVertexLocation2),false); //nft
		assertEquals(options.canPlaceTown(1, pieceVertexLocation2),false);
		assertEquals(options.canPlaceTown(2, pieceVertexLocation2),false);
		assertEquals(options.canPlaceTown(3, pieceVertexLocation2),false);
		
		assertEquals(options.canPlaceCity(0, pieceVertexLocation1), true);
		assertEquals(options.canPlaceCity(1, pieceVertexLocation1), false);
		assertEquals(options.canPlaceCity(2, pieceVertexLocation1), false);
		assertEquals(options.canPlaceCity(3, pieceVertexLocation1), false);
	}
	

}
